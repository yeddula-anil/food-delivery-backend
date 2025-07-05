package org.example.food.controller;

import org.example.food.DTO.RestaurantDto;
import org.example.food.model.Restaurant;
import org.example.food.model.User;
import org.example.food.service.RestaurantService;
import org.example.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    private UserService userService;
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader("Authorization") String jwt,
                                                       @RequestParam String keyword) throws Exception{
        User user=userService.findByJwtToken(jwt);
        List<Restaurant> restaurants=restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);

    }
    @GetMapping("/all-restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants(@RequestHeader("Authorization") String jwt) throws Exception{
        User user=userService.findByJwtToken(jwt);
        List<Restaurant> restaurants=restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestarantById(@RequestHeader("Authorization") String jwt,
                                                            @PathVariable Long id ) throws Exception{
        User user=userService.findByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);

    }
    @PutMapping("/{id}/add-favourites")
    public ResponseEntity<RestaurantDto> addToFavourites(@RequestHeader("Authorization") String jwt, Long id) throws Exception{
        User user=userService.findByJwtToken(jwt);
        RestaurantDto restaurant=restaurantService.addToFavourites(id,user);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


}
