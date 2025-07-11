package org.example.food.repository;

import org.example.food.model.Cart;
import org.example.food.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart_User_Id(Long userId);
}