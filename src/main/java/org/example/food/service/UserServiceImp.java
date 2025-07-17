package org.example.food.service;

import org.example.food.config.JwtProvider;
import org.example.food.exception.UserNotFoundException;
import org.example.food.model.User;
import org.example.food.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public User findByEmail(String email) {
       User user=userRepository.findByEmail(email);
       if(user==null){
           throw new UserNotFoundException("user not found"+email);
       }
       return user;
    }

    @Override
    public User findByJwtToken(String jwtToken) {
        try {
            String token = jwtToken.startsWith("Bearer ") ? jwtToken.substring(7) : jwtToken;
            String email = jwtProvider.getEmailFromJwtToken(token);
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + email);
            }
            return user;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired JWT token");
        }

    }
}
