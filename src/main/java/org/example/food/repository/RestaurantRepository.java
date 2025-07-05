package org.example.food.repository;

import org.example.food.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%',:query,'%') ) "+"OR lower(r.cuisineType) LIKE lower(concat('%',:query,'%') ) ")
    List<Restaurant> findBySearchQuery(String query);
    public Restaurant findRestaurantByOwnerId(Long ownerId);
}
