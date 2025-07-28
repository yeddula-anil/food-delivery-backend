package org.example.food.controller;

import org.example.food.model.Order;
import org.example.food.model.OrderItem;
import org.example.food.model.User;
import org.example.food.request.OrderRequest;
import org.example.food.service.OrderService;
import org.example.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @PostMapping("/order")
    public ResponseEntity<List<OrderItem>> createOrder(@RequestBody OrderRequest req,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        List<OrderItem> order=orderService.createOrder(req,user);
        return new ResponseEntity<>(order, HttpStatus.CREATED);

    }
    @GetMapping("/order/user")
    public ResponseEntity<List<OrderItem>> getOrders(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findByJwtToken(jwt);
        List<OrderItem> orders = orderService.getUserOrders(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }



}
