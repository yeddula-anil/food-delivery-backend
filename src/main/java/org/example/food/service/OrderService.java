package org.example.food.service;

import org.example.food.model.Order;
import org.example.food.model.User;
import org.example.food.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest req, User user) throws Exception;
    public Order updateOrder(Long orderId,String orderStatus) throws Exception;
    public Order cancelOrder(Long orderId) throws Exception;
    public List<Order> getUserOrders(Long userId) throws Exception;
    public List<Order> getRestaurantOrders(Long restaurantId,String orderStatus) throws Exception;
    public Order findOrderById(Long orderId) throws Exception;

}
