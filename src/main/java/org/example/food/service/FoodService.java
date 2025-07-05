package org.example.food.service;

import org.example.food.model.Food;
import org.example.food.model.Restaurant;
import org.example.food.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest request, Restaurant restaurant);
    public void deleteFood(Long foodId) throws Exception;
    public List<Food> getRestaurantFoods(Long restaurantId,boolean isVegetarian,boolean isNonVeg,boolean isSeasonal,
                                         String foodCategory) throws Exception;
    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long id) throws Exception;
    public Food updateAvailabilityStatus(Long foodId);


}
