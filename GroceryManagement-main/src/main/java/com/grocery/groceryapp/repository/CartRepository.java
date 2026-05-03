package com.grocery.groceryapp.repository;

import com.grocery.groceryapp.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserEmail(String userEmail);

    Optional<Cart> findByUserEmailAndItem_Id(String userEmail, Long itemId);

    Optional<Cart> findByIdAndUserEmail(Long id, String userEmail);

    void deleteByUserEmail(String userEmail);
}