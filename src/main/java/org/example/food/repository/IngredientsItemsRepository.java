package org.example.food.repository;

import org.example.food.model.IngredientsItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientsItemsRepository extends JpaRepository<IngredientsItems, Long> {
    public List<IngredientsItems> findIngredientsItemsByRestaurantId(Long id);
}
