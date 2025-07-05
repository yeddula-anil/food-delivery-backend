package org.example.food.service;

import org.example.food.model.IngredientsCategory;
import org.example.food.model.IngredientsItems;

import java.util.List;

public interface IngredientsService {
    public IngredientsCategory createIngredientCategory(String name, Long restaurantId) throws Exception;
    public IngredientsCategory findIngredientsByCategoryId(Long categoryId) throws Exception;
    public List<IngredientsCategory> findIngredientsCategoryByRestaurantId(Long restaurantId) throws Exception;
    public List<IngredientsItems> findIngredientsItemsByRestaurantId(Long restaurantId) throws Exception;
    public IngredientsItems createIngredientItem(Long restaurantId,String ingredientName,Long categoryId) throws Exception;
    public IngredientsItems updateStock(Long id) throws Exception;
}
