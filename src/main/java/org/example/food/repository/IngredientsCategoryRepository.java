package org.example.food.repository;

import org.example.food.model.IngredientsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientsCategoryRepository extends JpaRepository<IngredientsCategory, Long> {
    public List<IngredientsCategory> findIngredientsCategoryByRestaurantId(Long restaurantId) throws Exception;

}
