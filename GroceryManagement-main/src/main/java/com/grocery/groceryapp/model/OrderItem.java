package com.grocery.groceryapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private double price;

    // 🔗 Many items belong to one product
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    // 🔗 Many order items belong to one order
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    // ===== GETTERS =====
    public Long getId() { return id; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public Item getItem() { return item; }
    public Order getOrder() { return order; }

    // ===== SETTERS =====
    public void setId(Long id) { this.id = id; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }
    public void setItem(Item item) { this.item = item; }
    public void setOrder(Order order) { this.order = order; }
}