package org.example.food.controller;

import org.example.food.DTO.RevenueByCategoryDto;
import org.example.food.model.Restaurant;
import org.example.food.model.User;
import org.example.food.service.RestaurantService;
import org.example.food.service.RevenueService;
import org.example.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin")
public class RevenueController {
    @Autowired
    private RevenueService revenueService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;


    @GetMapping("/restaurant/revenue")
    public ResponseEntity<Map<String, Double>> getRestaurantRevenue(@RequestHeader("Authorization")  String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        Restaurant res=restaurantService.findRestaurantByUserId(user.getId());
        Map<String, Double> revenue = revenueService.getRestaurantRevenue(res.getId());
        return new ResponseEntity<>(revenue, HttpStatus.OK);
    }

    @GetMapping("/restaurant/category-revenue")
    public ResponseEntity<Map<String, Map<String, Double>>> getRestaurantCategoryRevenue(@RequestHeader("Authorization")  String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        Restaurant res=restaurantService.findRestaurantByUserId(user.getId());
        Map<String, Map<String, Double>> revenue = revenueService.getRestaurantCategoryRevenue(res.getId());
        return new ResponseEntity<>(revenue, HttpStatus.OK);
    }

    @GetMapping("/revenue/monthly")
    public ResponseEntity<Map<String, Double>> getMonthlyRevenue(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        Restaurant res=restaurantService.findRestaurantByUserId(user.getId());


        return ResponseEntity.ok(revenueService.getMonthlyRevenueByRestaurant(res.getId()));
    }




}
