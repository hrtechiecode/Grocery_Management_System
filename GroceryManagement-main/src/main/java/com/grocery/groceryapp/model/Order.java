package com.grocery.groceryapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;
    private double totalPrice;
    private LocalDateTime createdAt;

    // ⭐ NEW FIELD (IMPORTANT)
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    // ===== GETTERS =====

    public Long getId() { return id; }

    public String getUserEmail() { return userEmail; }

    public double getTotalPrice() { return totalPrice; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public String getStatus() { return status; }

    public List<OrderItem> getOrderItems() { return orderItems; }

    // ===== SETTERS =====

    public void setId(Long id) { this.id = id; }

    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public void setStatus(String status) { this.status = status; }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}