package org.example.food.service;

import org.example.food.model.Category;
import org.example.food.model.Food;
import org.example.food.model.Restaurant;
import org.example.food.repository.FoodRepository;
import org.example.food.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private CategoryService categoryService;

    @Override
    public Food createFood(CreateFoodRequest request, Restaurant restaurant) throws Exception {
        Category category=categoryService.findByCategoryId(request.getCategoryId());
        if(category==null){
            throw new Exception("Category not found");
        }
        Food f = new Food();
        f.setName(request.getName());
        f.setRestaurant(restaurant);
        f.setFoodCategory(category);
        f.setDescription(request.getDescription());
        f.setPrice(request.getPrice());
        f.setAvailable(true);
        f.setSeasonal(request.isSeasonal());
        f.setVegetarian(request.isVegetarian());
        f.setImages(request.getImages());

        // 🔥 Set the creation date here
        f.setCreationDate(new Date());
        System.out.println(f.isAvailable());

        return foodRepository.save(f);
    }


    @Override
    public List<Food> getRestaurantFoods(Long restaurantId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal, String foodCategory) throws Exception {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
        System.out.println("Initial foods count: " + foods.size());

        if (isVegetarian) {
            foods = filterByVegetarian(foods);
            System.out.println("After vegetarian filter: " + foods.size());
        }

        if (isNonVeg) {
            foods = filterByNonVeg(foods);
            System.out.println("After non-veg filter: " + foods.size());
        }

        if (isSeasonal) {
            foods = filterBySeasonal(foods);
            System.out.println("After seasonal filter: " + foods.size());
        }

        if (foodCategory != null && !foodCategory.isBlank()) {
            foods = filterByCategory(foods, foodCategory);
            System.out.println("After category filter: " + foods.size());
        }

        return foods;
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    // Filter only vegetarian items
    public List<Food> filterByVegetarian(List<Food> foods) {
        return foods.stream()
                .filter(food->food.isVegetarian())
                .collect(Collectors.toList());
    }

    // Filter only non-vegetarian items
    public List<Food> filterByNonVeg(List<Food> foods) {
        return foods.stream()
                .filter(food -> !food.isVegetarian())
                .collect(Collectors.toList());
    }

    // Filter seasonal items
    public List<Food> filterBySeasonal(List<Food> foods) {
        return foods.stream()
                .filter(Food::isSeasonal)
                .collect(Collectors.toList());
    }

    // Filter by category name
    public List<Food> filterByCategory(List<Food> foods, String category) {
        return foods.stream()
                .filter(food -> food.getFoodCategory() != null &&
                        food.getFoodCategory().getName().trim().equalsIgnoreCase(category.trim()))
                .collect(Collectors.toList());

    }


    @Override
    public Food findFoodById(Long id) throws Exception {
        return foodRepository.findById(id)
                .orElseThrow(() -> new Exception("Food not found"));
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception{
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new Exception("Food not found"));
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new Exception("Food not found with id: " + foodId));

        // Optional: Detach the food from the restaurant to avoid foreign key constraint issues
        food.setRestaurant(null);
        foodRepository.delete(food);
    }

}
