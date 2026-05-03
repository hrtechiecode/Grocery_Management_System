package com.grocery.groceryapp.repository;

import com.grocery.groceryapp.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // 🔍 Search by name
    List<Item> findByNameContainingIgnoreCase(String name);

    // 📂 Filter by category
    List<Item> findByCategory(String category);

    // 🔥 Low stock directly from DB
    List<Item> findByQuantityLessThan(int threshold);
}