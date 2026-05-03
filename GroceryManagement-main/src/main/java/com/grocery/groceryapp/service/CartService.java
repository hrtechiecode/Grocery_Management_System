package com.grocery.groceryapp.service;

import com.grocery.groceryapp.model.Cart;
import com.grocery.groceryapp.model.Item;
import com.grocery.groceryapp.repository.CartRepository;
import com.grocery.groceryapp.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    // ✅ Add to Cart (SMART)
    public Cart addToCart(Long itemId, int quantity, String email) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // 🔥 STOCK CHECK
        if (item.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock available");
        }

        // 🔥 CHECK DUPLICATE
        Cart existingCart = cartRepository
                .findByUserEmailAndItem_Id(email, itemId)
                .orElse(null);

        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            existingCart.setPrice(item.getPrice());
            return cartRepository.save(existingCart);
        }

        // 🔥 NEW CART ITEM
        Cart cart = new Cart();
        cart.setUserEmail(email);
        cart.setItem(item);
        cart.setQuantity(quantity);
        cart.setPrice(item.getPrice());

        return cartRepository.save(cart);
    }

    // ✅ Get Cart
    public List<Cart> getCartByUser(String email) {
        return cartRepository.findByUserEmail(email);
    }

    // ✅ Remove Item (SECURE)
    public void removeItem(Long id, String email) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!cart.getUserEmail().equals(email)) {
            throw new RuntimeException("Unauthorized action");
        }

        cartRepository.delete(cart);
    }

    // ✅ Clear Cart
    public void clearCart(String email) {
        cartRepository.deleteByUserEmail(email);
    }
}