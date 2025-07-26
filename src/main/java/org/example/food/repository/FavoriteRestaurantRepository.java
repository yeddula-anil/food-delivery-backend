package org.example.food.repository;

import org.example.food.model.FavoriteRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface FavoriteRestaurantRepository extends JpaRepository<FavoriteRestaurant, Long> {
}

