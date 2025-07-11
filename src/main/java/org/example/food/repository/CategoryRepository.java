package org.example.food.repository;

import org.example.food.model.Category;
import org.example.food.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByRestaurant(Restaurant restaurant);


}
