package org.example.food.service;

import org.example.food.DTO.RevenueByCategoryDto;
import org.example.food.model.Order;
import org.example.food.model.OrderItem;
import org.example.food.model.OrderStatus;
import org.example.food.repository.OrderItemRepository;
import org.example.food.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RevenueServiceImpl implements RevenueService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Override
    public Map<String, Double> getRestaurantRevenue(Long restaurantId) {
        List<OrderItem> allOrders = orderItemRepository.findByRestaurantIdAndStatus(restaurantId, OrderStatus.COMPLETED);

        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();

        double daily = allOrders.stream()
                .filter(o -> o.getOrderDate().toLocalDate().isEqual(today))
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();

        double monthly = allOrders.stream()
                .filter(o -> o.getOrderDate().getYear() == currentYear &&
                        o.getOrderDate().getMonthValue() == currentMonth)
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();

        double yearly = allOrders.stream()
                .filter(o -> o.getOrderDate().getYear() == currentYear)
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();

        Map<String, Double> revenue = new HashMap<>();
        revenue.put("daily", daily);
        revenue.put("monthly", monthly);
        revenue.put("yearly", yearly);
        return revenue;
    }

    @Override
    public Map<String, Map<String, Double>> getRestaurantCategoryRevenue(Long restaurantId) {
        List<OrderItem> orders = orderItemRepository.findByRestaurantIdAndStatus(restaurantId, OrderStatus.COMPLETED);

        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();

        Map<String, Map<String, Double>> revenue = new HashMap<>();
        revenue.put("daily", new HashMap<>());
        revenue.put("monthly", new HashMap<>());
        revenue.put("yearly", new HashMap<>());

        for (OrderItem order : orders) {
            String category = order.getFood().getFoodCategory().getName();
            double price = order.getTotalPrice();
            LocalDate orderDate = order.getOrderDate().toLocalDate();

            // Daily
            if (orderDate.isEqual(today)) {
                revenue.get("daily").merge(category, price, Double::sum);
            }

            // Monthly
            if (orderDate.getYear() == currentYear && orderDate.getMonthValue() == currentMonth) {
                revenue.get("monthly").merge(category, price, Double::sum);
            }

            // Yearly
            if (orderDate.getYear() == currentYear) {
                revenue.get("yearly").merge(category, price, Double::sum);
            }
        }

        return revenue;
    }


    @Override
    public Map<String, Double> getMonthlyRevenueByRestaurant(Long restaurantId) {
        List<Object[]> results = orderItemRepository.getMonthlyRevenueByRestaurant(restaurantId);
        Map<String, Double> revenueMap = new LinkedHashMap<>();

        for (Object[] row : results) {
            String month = (String) row[0]; // month name as string
            Number total = (Number) row[1]; // Safely cast to Number
            revenueMap.put(month, total.doubleValue()); // convert to double
        }
        return revenueMap;
    }







}
