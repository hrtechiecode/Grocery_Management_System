package com.grocery.groceryapp.repository;

import com.grocery.groceryapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // ✅ Get orders of a specific user
    List<Order> findByUserEmail(String userEmail);

    // 🔥 Latest orders first
    List<Order> findByUserEmailOrderByCreatedAtDesc(String userEmail);

    // 📊 Total orders count per user
    long countByUserEmail(String userEmail);

    // 💰 TOTAL REVENUE (ADMIN DASHBOARD FIX)
    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o")
    Double getTotalRevenue();
}