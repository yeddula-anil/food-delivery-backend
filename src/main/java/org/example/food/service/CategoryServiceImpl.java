package org.example.food.service;

import org.example.food.model.Category;
import org.example.food.model.Restaurant;
import org.example.food.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public Category createCategory(String name, Long userId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantByUserId(userId);
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        return categoryRepository.findByRestaurant(restaurant); // ✅ direct call to repository
    }

    @Override
    public Category findByCategoryId(Long categoryId) throws Exception {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new Exception("Category not found");
        }
        return category.get();
    }
}

