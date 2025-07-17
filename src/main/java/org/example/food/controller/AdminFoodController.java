package org.example.food.controller;

import org.example.food.model.Food;
import org.example.food.model.Restaurant;
import org.example.food.model.User;
import org.example.food.repository.FoodRepository;
import org.example.food.request.CreateFoodRequest;
import org.example.food.response.MessageResponse;
import org.example.food.service.FoodService;
import org.example.food.service.RestaurantService;
import org.example.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")

public class AdminFoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;
    @PostMapping()
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request, @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(request.getRestaurantId());
        Food food=foodService.createFood(request,restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);

    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<MessageResponse> createFood(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        foodService.deleteFood(id);
        MessageResponse msg=new MessageResponse();
        msg.setMessage("Successfully deleted food item");

        return new ResponseEntity<>(msg, HttpStatus.CREATED);

    }
    @PutMapping("")
    public ResponseEntity<Food> updateFoodAvailabilityStatus(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        Food food=foodService.updateAvailabilityStatus(id);
        return new ResponseEntity<>(food, HttpStatus.OK);

    }

}
