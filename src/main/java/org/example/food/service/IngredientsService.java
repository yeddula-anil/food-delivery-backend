package org.example.food.service;

import org.example.food.model.IngredientsCategory;
import org.example.food.model.IngredientsItems;
import org.example.food.model.Restaurant;
import org.example.food.request.IngredientsItemRequest;

import java.util.List;

public interface IngredientsService {
//    public IngredientsCategory createIngredientCategory(String name, Long restaurantId) throws Exception;
    public IngredientsCategory findIngredientsByCategoryId(Long categoryId) throws Exception;
    public List<IngredientsCategory> findIngredientsCategoryByRestaurantId(Long restaurantId) throws Exception;
    public List<IngredientsItems> findIngredientsItemsByRestaurantId(Long restaurantId) throws Exception;
    public List<IngredientsItems> createIngredients(List<IngredientsItemRequest> req, Restaurant res, Long foodId) throws Exception;
    public IngredientsItems updateStock(Long id) throws Exception;
}
