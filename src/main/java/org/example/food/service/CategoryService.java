package org.example.food.service;

import org.example.food.model.Category;

import java.util.List;

public interface CategoryService {
    public Category createCategory(String name, Long userId) throws Exception;
    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception;
    public Category findByCategoryId(Long categoryId) throws Exception;
}
