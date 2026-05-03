package com.grocery.groceryapp.service;

import com.grocery.groceryapp.model.*;
import com.grocery.groceryapp.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    // 🧾 USER CHECKOUT (already correct)
    @Transactional
    public Order checkout(String email) {

        List<Cart> cartItems = cartRepository.findByUserEmail(email);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUserEmail(email);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("PENDING"); // ⭐ important

        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (Cart c : cartItems) {

            Item item = itemRepository.findById(c.getItem().getId())
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            if (item.getQuantity() < c.getQuantity()) {
                throw new RuntimeException("Insufficient stock for " + item.getName());
            }

            item.setQuantity(item.getQuantity() - c.getQuantity());
            itemRepository.save(item);

            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setItem(item);
            oi.setQuantity(c.getQuantity());
            oi.setPrice(item.getPrice());

            total += item.getPrice() * c.getQuantity();

            orderItems.add(oi);
        }

        order.setTotalPrice(total);
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        cartRepository.deleteAll(cartItems);

        return savedOrder;
    }

    // 📋 ADMIN: Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 👀 ADMIN: Get order by ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // 🔄 ADMIN: Update order status
    public Order updateOrderStatus(Long id, String status) {

        Order order = getOrderById(id);

        order.setStatus(status); // PENDING / SHIPPED / DELIVERED

        return orderRepository.save(order);
    }

    // ❌ ADMIN: Delete order
    public void deleteOrder(Long id) {

        Order order = getOrderById(id);

        orderRepository.delete(order);
    }
}