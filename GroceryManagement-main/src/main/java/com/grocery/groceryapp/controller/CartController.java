package com.grocery.groceryapp.controller;

import com.grocery.groceryapp.model.Cart;
import com.grocery.groceryapp.service.CartService;
import com.grocery.groceryapp.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final JwtUtil jwtUtil;

    // 🔐 Extract email safely from JWT
    private String getEmailFromToken(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = header.substring(7);
        return jwtUtil.extractEmail(token);
    }

    // ➕ ADD TO CART
    @PostMapping("/add")
    public Cart addToCart(@RequestParam Long itemId,
                          @RequestParam int quantity,
                          HttpServletRequest request) {

        String email = getEmailFromToken(request);
        return cartService.addToCart(itemId, quantity, email);
    }

    // 📦 GET CART
    @GetMapping
    public List<Cart> getCart(HttpServletRequest request) {

        String email = getEmailFromToken(request);
        return cartService.getCartByUser(email);
    }

    // 🗑️ REMOVE ITEM
    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id,
                             HttpServletRequest request) {

        String email = getEmailFromToken(request);
        cartService.removeItem(id, email);

        return "Item removed successfully";
    }
}