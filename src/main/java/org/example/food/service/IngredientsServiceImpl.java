package org.example.food.service;

import org.example.food.model.IngredientsCategory;
import org.example.food.model.IngredientsItems;
import org.example.food.model.Restaurant;
import org.example.food.repository.IngredientsCategoryRepository;
import org.example.food.repository.IngredientsItemsRepository;
import org.example.food.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public IngredientsCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory ingredientsCategory = new IngredientsCategory();
        ingredientsCategory.setName(name);
        ingredientsCategory.setRestaurant(restaurant);
        return ingredientsCategoryRepository.save(ingredientsCategory);
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
    public IngredientsItems createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory ingredientsCategory=findIngredientsByCategoryId(categoryId);
        IngredientsItems item=new IngredientsItems();
        item.setRestaurant(restaurant);
        item.setName(ingredientName);
        item.setCategory(ingredientsCategory);
        IngredientsItems ingredientItem=ingredientsItemsRepository.save(item);
        ingredientsCategory.getIngredients().add(ingredientItem);
        return item;
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
