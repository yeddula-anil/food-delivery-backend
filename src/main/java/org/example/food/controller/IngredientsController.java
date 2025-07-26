package org.example.food.controller;

import org.apache.tomcat.util.http.parser.Authorization;
import org.example.food.model.IngredientsCategory;
import org.example.food.model.IngredientsItems;
import org.example.food.model.Restaurant;
import org.example.food.model.User;
import org.example.food.request.IngredientsCategoryRequest;
import org.example.food.request.IngredientsItemRequest;

import org.example.food.service.IngredientsService;
import org.example.food.service.RestaurantService;
import org.example.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")

public class IngredientsController {
    @Autowired
    private IngredientsService ingredientsService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;


    @PostMapping("/{id}")
    public ResponseEntity<List<IngredientsItems>> createItem(@RequestBody List<IngredientsItemRequest> req,@RequestHeader("Authorization") String jwt,@PathVariable("id") Long id) throws Exception{
        User user=userService.findByJwtToken(jwt);
        Restaurant res=restaurantService.findRestaurantByUserId(user.getId());
        List<IngredientsItems> items=ingredientsService.createIngredients(req,res,id);
        return new ResponseEntity<>(items, HttpStatus.CREATED);


    }
    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItems> updateIngredientsStock(@PathVariable Long id,@RequestHeader("Authorization") String jwt) throws Exception{
        IngredientsItems item=ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);

    }
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItems>> getRestaurantIngredients(@PathVariable Long id,@RequestHeader("Authorization") String jwt) throws Exception{
        List<IngredientsItems> items=ingredientsService.findIngredientsItemsByRestaurantId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);

    }
    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientsCategory>> getRestaurantIngredientsCategory(@PathVariable Long id,@RequestHeader("Authorization") String jwt) throws Exception{
        List<IngredientsCategory> categories=ingredientsService.findIngredientsCategoryByRestaurantId(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
