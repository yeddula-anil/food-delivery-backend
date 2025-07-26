package org.example.food.service;

import org.example.food.model.Food;
import org.example.food.model.IngredientsCategory;
import org.example.food.model.IngredientsItems;
import org.example.food.model.Restaurant;
import org.example.food.repository.FoodRepository;
import org.example.food.repository.IngredientsCategoryRepository;
import org.example.food.repository.IngredientsItemsRepository;
import org.example.food.repository.RestaurantRepository;
import org.example.food.request.IngredientsItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImpl implements IngredientsService {
    @Autowired
    private IngredientsCategoryRepository ingredientsCategoryRepository;
    @Autowired
    private IngredientsItemsRepository ingredientsItemsRepository;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public List<IngredientsItems> createIngredients(List<IngredientsItemRequest> req,Restaurant res,Long foodId) throws Exception {
        List<IngredientsItems> savedItems = new ArrayList<>();
        Food food = foodService.findFoodById(foodId);
        if(food==null){
            throw new Exception("Food not found");
        }

        for (IngredientsItemRequest item : req) {
            IngredientsItems ingredient = new IngredientsItems();
            ingredient.setName(item.getName());
            ingredient.setCategory(item.getCategory());
            ingredient.setFood(food);


            ingredient.setRestaurant(res);
            IngredientsItems saved = ingredientsItemsRepository.save(ingredient);
            savedItems.add(saved);
            food.getIngredients().add(saved);


        }
        foodRepository.save(food);
        return savedItems;
    }

    @Override
    public IngredientsCategory findIngredientsByCategoryId(Long categoryId) throws Exception {
        Optional<IngredientsCategory> optional=ingredientsCategoryRepository.findById(categoryId);
        if(optional.isEmpty()){
            throw new Exception("ingredients category not found");
        }
        return optional.get();
    }

    @Override
    public List<IngredientsCategory> findIngredientsCategoryByRestaurantId(Long restaurantId) throws Exception {
        restaurantService.findRestaurantById(restaurantId);
        return ingredientsCategoryRepository.findIngredientsCategoryByRestaurantId(restaurantId);

    }

    @Override
    public List<IngredientsItems> findIngredientsItemsByRestaurantId(Long restaurantId) throws Exception {
        return ingredientsItemsRepository.findIngredientsItemsByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItems updateStock(Long id) throws Exception {
        Optional<IngredientsItems> optional=ingredientsItemsRepository.findById(id);
        if(optional.isEmpty()){
            throw new Exception("ingredients item not found");
        }
        IngredientsItems item=optional.get();
        item.setInStock(!item.isInStock());
        return ingredientsItemsRepository.save(item);
    }



}
