package com.grocery.groceryapp.controller;

import com.grocery.groceryapp.model.Order;
import com.grocery.groceryapp.security.JwtUtil;
import com.grocery.groceryapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final JwtUtil jwtUtil;

    // 🔐 Safe JWT extraction
    private String getEmail(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = header.substring(7);
        return jwtUtil.extractEmail(token);
    }

    // 📦 GET ALL ORDERS (ADMIN only ideally)
    @GetMapping
    public List<Order> getAll() {
        return orderService.getAllOrders();
    }

    // 🔥 CHECKOUT (USER)
    @PostMapping("/checkout")
    public Order checkout(HttpServletRequest request) {
        String email = getEmail(request);
        return orderService.checkout(email);
    }
}