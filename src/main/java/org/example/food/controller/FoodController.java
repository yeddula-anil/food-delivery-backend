package org.example.food.controller;

import org.example.food.model.Food;
import org.example.food.model.User;
import org.example.food.service.FoodService;
import org.example.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/food")
public class FoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @GetMapping("/search")
    public ResponseEntity<Food> searchFood(@RequestParam String keyword, @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        List<Food> foods=foodService.searchFood(keyword);
        return new ResponseEntity<>(foods.get(0), HttpStatus.OK);

    }
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@RequestParam boolean vegetarian,
                                                  @RequestParam boolean seasonal,
                                                  @RequestParam boolean nonveg,
                                                  @RequestParam Long restaurantId,
                                                  @RequestParam String foodCategory,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        List<Food> foods=foodService.getRestaurantFoods(restaurantId,vegetarian,nonveg,seasonal,foodCategory);
        return new ResponseEntity<>(foods, HttpStatus.OK);

    }

}
