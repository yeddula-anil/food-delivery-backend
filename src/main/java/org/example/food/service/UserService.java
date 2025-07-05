package org.example.food.service;

import org.example.food.model.User;

public interface UserService {
    public User findByEmail(String email);
    public User findByJwtToken(String jwtToken) throws Exception;
}
