package org.example.food.controller;

import org.example.food.model.Order;
import org.example.food.model.User;
import org.example.food.service.OrderService;
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
    @GetMapping("/order/user/restaurant/{id}")
    public ResponseEntity<List<Order>> getRestaurantOrder(@PathVariable Long id, @RequestParam(required = false) String orderStatus,
                                                          @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        List<Order> order=orderService.getRestaurantOrders(id, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @PathVariable  String orderStatus,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        Order order=orderService.updateOrder(id,orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);


    }

}
