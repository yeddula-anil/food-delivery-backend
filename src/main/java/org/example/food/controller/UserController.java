package org.example.food.controller;

import org.example.food.model.User;
import org.example.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private  UserService userService;
    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String jwtHeader) throws Exception {
        // ✅ Strip "Bearer " prefix if present


        User user = userService.findByJwtToken(jwtHeader);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
