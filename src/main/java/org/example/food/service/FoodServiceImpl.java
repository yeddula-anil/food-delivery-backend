package org.example.food.service;

import org.example.food.model.Food;
import org.example.food.model.Restaurant;
import org.example.food.repository.FoodRepository;
import org.example.food.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest request, Restaurant restaurant) {
        Food f=new Food();
        f.setName(request.getName());
        f.setRestaurant(restaurant);
        f.setDescription(request.getDescription());
        f.setPrice(request.getPrice());
        f.setIngredients(request.getIngredients());
        f.setSeasonal(request.isSeasonal());
        f.setVegetarian(request.isVegetarian());
        f.setImages(request.getImages());
        Food saved=foodRepository.save(f);
        restaurant.getFoods().add(f);
        return saved;

    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food f=foodRepository.findById(foodId).get();
        f.setRestaurant(null);
        foodRepository.save(f);

    }

    @Override
    public List<Food> getRestaurantFoods(Long restaurantId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal, String foodCategory) throws Exception {
        List<Food> foods=foodRepository.findByRestaurantId(restaurantId);
        if(isVegetarian){
            foods=filterByVegetarian(foods,isVegetarian);
        }
        if(isNonVeg){
            foods=filterByNonVeg(foods,isNonVeg);
        }
        if(isSeasonal){
            foods=filterBySeasonal(foods,isSeasonal);
        }
        if(foodCategory!=null && !foodCategory.equals("")){
            foods=filterByCategory(foods,foodCategory);

        }
        return foods;
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }



   public List<Food> filterByVegetarian(List<Food> foods,boolean isVegetarian){

        return foods.stream().filter(food->food.isVegetarian()==isVegetarian).collect(Collectors.toList());
   }
   public List<Food> filterByNonVeg(List<Food> foods,boolean isNonVeg){
        return foods.stream().filter(food->!food.isVegetarian()==false).collect(Collectors.toList());
   }
   public List<Food> filterBySeasonal(List<Food> foods,boolean isSeasonal){
        return foods.stream().filter(food->!food.isSeasonal()).collect(Collectors.toList());
   }
   public List<Food> filterByCategory(List<Food> foods,String category){
        return foods.stream().filter(food->{
            if(food.getFoodCategory()!=null){
                return food.getFoodCategory().getName().equals(category);
            }
            return false;
        }).collect(Collectors.toList());
   }
    @Override
    public Food findFoodById(Long id) throws Exception {
        Optional<Food> food=foodRepository.findById(id);
        if(food.isEmpty()){
                throw new Exception("food not found");
        }
        return food.get();
    }
    @Override
    public Food updateAvailabilityStatus(Long foodId){
            Food food=foodRepository.findById(foodId).get();
            food.setAvailable(!food.isAvailable());
            return foodRepository.save(food);
    }





}