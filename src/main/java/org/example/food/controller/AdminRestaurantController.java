package org.example.food.controller;

import org.example.food.model.Restaurant;
import org.example.food.model.User;
import org.example.food.request.CreateRestaurantRequest;
import org.example.food.response.MessageResponse;
import org.example.food.service.RestaurantService;
import org.example.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/restaurant")
public class AdminRestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;
    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest request,
                                                       @RequestHeader("Authorization") String jwt) throws Exception{
        User user=userService.findByJwtToken(jwt);
        Restaurant restaurant=restaurantService.createRestaurant(request,user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);



    }
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest request,
                                                       @RequestHeader("Authorization") String jwt,
                                                       @PathVariable Long id) throws Exception{
        User user=userService.findByJwtToken(jwt);
        Restaurant restaurant=restaurantService.updateRestaurant(id,request);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);

    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<MessageResponse> deleteRestaurant(@RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception{
        User user=userService.findByJwtToken(jwt);
        restaurantService.deleteRestaurantById(id);
        MessageResponse msg=new MessageResponse();
        msg.setMessage("Restaurant deleted");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
    @PutMapping("{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception{
        User user=userService.findByJwtToken(jwt);
        Restaurant res=restaurantService.findRestaurantById(id);
        Restaurant restaurant=restaurantService.updateRestaurantStatus(res.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(@RequestHeader("Authorization") String jwt) throws Exception{
        User user=userService.findByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantByUserId(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


}
