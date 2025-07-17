package org.example.food.service;

import org.example.food.model.FavoriteRestaurant;
import org.example.food.model.User;
import org.springframework.stereotype.Service;


public interface FavoriteRestaurantService {

    public FavoriteRestaurant addToFavorites(Long restaurantId,String jwt) throws Exception;
}
