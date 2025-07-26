package org.example.food.service;

import org.example.food.DTO.RevenueByCategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public interface RevenueService {
    public List<Map<String, Object>> getRevenueByCategoryList(Long restaurantId);
    public List<Map<String, Object>> getDailyRevenue(Long restaurantId) ;
    public List<Map<String, Object>> getMonthlyRevenue(Long restaurantId);
    public List<Map<String, Object>> getYearlyRevenue(Long restaurantId);
    public List<RevenueByCategoryDto> getCategoryWiseRevenue(Long restaurantId);
}
