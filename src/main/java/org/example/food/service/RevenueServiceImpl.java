package org.example.food.service;

import org.example.food.DTO.RevenueByCategoryDto;
import org.example.food.model.Order;
import org.example.food.model.OrderItem;
import org.example.food.model.OrderStatus;
import org.example.food.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class RevenueServiceImpl implements RevenueService {

    @Autowired
    OrderRepository orderRepository;
    @Override
    public List<Map<String, Object>> getRevenueByCategoryList(Long restaurantId) {
        List<Object[]> results = orderRepository.getRevenueByCategory(restaurantId);
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("category", row[0]);
            map.put("revenue", row[1]);
            response.add(map);
        }
        return response;
    }

    @Override
    public List<Map<String, Object>> getDailyRevenue(Long restaurantId) {
        List<Object[]> result = orderRepository.getDailyRevenue(restaurantId);
        return result.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("date", row[0]);
            map.put("revenue", row[1]);
            return map;
        }).collect(Collectors.toList());
    }
    @Override
    public List<Map<String, Object>> getMonthlyRevenue(Long restaurantId) {
        List<Object[]> result = orderRepository.getMonthlyRevenue(restaurantId);
        return result.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("month", row[0]);
            map.put("year", row[1]);
            map.put("revenue", row[2]);
            return map;
        }).collect(Collectors.toList());
    }
    @Override
    public List<Map<String, Object>> getYearlyRevenue(Long restaurantId) {
        List<Object[]> result = orderRepository.getYearlyRevenue(restaurantId);
        return result.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("year", row[0]);
            map.put("revenue", row[1]);
            return map;
        }).collect(Collectors.toList());
    }
    @Override
    public List<RevenueByCategoryDto> getCategoryWiseRevenue(Long restaurantId) {
        List<Order> orders = orderRepository.findByRestaurantIdAndOrderStatus(restaurantId, OrderStatus.COMPLETED);

        Map<String, RevenueByCategoryDto> revenueMap = new HashMap<>();

        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        for (Order order : orders) {
            if (order.getItems() == null) continue;

            LocalDate orderDate = order.getOrderDate().toLocalDate();

            for (OrderItem item : order.getItems()) {
                String category = item.getFood().getFoodCategory().getName();
                long amount = item.getFood().getPrice() * item.getQuantity();

                RevenueByCategoryDto dto = revenueMap.getOrDefault(category, new RevenueByCategoryDto(category, 0L, 0L, 0L));

                if (orderDate.isEqual(today)) {
                    dto.setDailyRevenue(dto.getDailyRevenue() + amount);
                }

                if (orderDate.getMonthValue() == currentMonth && orderDate.getYear() == currentYear) {
                    dto.setMonthlyRevenue(dto.getMonthlyRevenue() + amount);
                }

                if (orderDate.getYear() == currentYear) {
                    dto.setYearlyRevenue(dto.getYearlyRevenue() + amount);
                }

                revenueMap.put(category, dto);
            }
        }

        return new ArrayList<>(revenueMap.values());
    }



}
