package org.example.food.controller;

import org.example.food.model.Order;
import org.example.food.model.OrderItem;
import org.example.food.model.Restaurant;
import org.example.food.model.User;
import org.example.food.service.OrderService;
import org.example.food.service.RestaurantService;
import org.example.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/orders/restaurant")
    public ResponseEntity<List<OrderItem>> getRestaurantOrder(
                                                          @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        Restaurant res=restaurantService.findRestaurantByUserId(user.getId());
        List<OrderItem> order=orderService.getRestaurantOrders(res.getId());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<OrderItem> updateOrderStatus(@PathVariable Long id, @PathVariable  String orderStatus,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        OrderItem order=orderService.updateOrder(id,orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);


    }

}
