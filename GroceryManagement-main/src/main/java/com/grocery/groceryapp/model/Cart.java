package com.grocery.groceryapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private int quantity;
    private double price;

    // 🔗 Relation with Item
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    // ===== GETTERS =====
    public Long getId() { return id; }
    public String getUserEmail() { return userEmail; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public Item getItem() { return item; }

    // ===== SETTERS =====
    public void setId(Long id) { this.id = id; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }
    public void setItem(Item item) { this.item = item; }
}