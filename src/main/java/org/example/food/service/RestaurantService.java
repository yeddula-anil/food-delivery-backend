package org.example.food.service;

import org.example.food.DTO.RestaurantDto;
import org.example.food.model.Restaurant;
import org.example.food.model.User;
import org.example.food.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);
    public Restaurant updateRestaurant(Long restauarantId,CreateRestaurantRequest updatedRestaurant) throws Exception;
    public void deleteRestaurantById(Long restaurantId) throws Exception;
    public List<Restaurant> getAllRestaurants();
    public List<Restaurant> searchRestaurant(String keyword);
    public Restaurant findRestaurantById(Long restaurantId) throws Exception;
    public Restaurant findRestaurantByUserId(Long userId) throws Exception;
//    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception;
    public Restaurant updateRestaurantStatus(Long id) throws Exception;

}
