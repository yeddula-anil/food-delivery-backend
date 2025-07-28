package org.example.food.service;

import org.example.food.DTO.RevenueByCategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public interface RevenueService {
    public Map<String, Double> getRestaurantRevenue(Long restaurantId);
    public Map<String, Map<String, Double>> getRestaurantCategoryRevenue(Long restaurantId);
    public Map<String, Double> getMonthlyRevenueByRestaurant(Long restaurantId);
}
