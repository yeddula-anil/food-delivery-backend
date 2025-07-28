package org.example.food.service;

import org.example.food.model.Order;
import org.example.food.model.OrderItem;
import org.example.food.model.User;
import org.example.food.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public List<OrderItem> createOrder(OrderRequest req, User user) throws Exception;
    public OrderItem updateOrder(Long orderId,String orderStatus) throws Exception;
    public OrderItem cancelOrder(Long orderId) throws Exception;
    public List<OrderItem> getUserOrders(Long userId) throws Exception;
    public List<OrderItem> getRestaurantOrders(Long restaurantId) throws Exception;
    public OrderItem findOrderById(Long orderId) throws Exception;

}
