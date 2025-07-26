package org.example.food.service;

import org.example.food.model.*;
import org.example.food.repository.*;
import org.example.food.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private AdressRepository adressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest req, User user) throws Exception {
        Adress shippingAdress=req.getDeliveryAdress();
        Adress savedAdress=adressRepository.save(shippingAdress);
        if(!user.getAdresses().contains(savedAdress)){
            user.getAdresses().add(savedAdress);
            userRepository.save(user);
        }
        Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantId());
        Order createdOrder=new Order();
        createdOrder.setRestaurant(restaurant);
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setAdress(savedAdress);
        createdOrder.setAdress(shippingAdress);
        Cart cart=cartService.findCartByUserId(user.getId());
        List<OrderItem> orderItems=new ArrayList<OrderItem>();
        for(CartItem cartItem:cart.getItems()){
            OrderItem order=new OrderItem();
            order.setFood(cartItem.getFood());
            order.setQuantity(cartItem.getQuantity());
            order.setQuantity(cartItem.getQuantity());
            order.setTotalPrice(cartItem.getTotalprice());
            OrderItem savedOrderItem=orderItemRepository.save(order);
            orderItems.add(savedOrderItem);
        }
        Long totalprice=cartService.calculateCartTotal(cart);
        createdOrder.setItems(orderItems);
        createdOrder.setOrderStatus(OrderStatus.COMPLETED);
        createdOrder.setTotalprice(totalprice);
        createdOrder.setOrderDate(LocalDateTime.now());
        System.out.println("Order status before save: " + createdOrder.getOrderStatus());

        Order savedOrder=orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);
        restaurantRepository.save(restaurant);
        return savedOrder;



    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order=findOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED") || orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING")){
            order.setOrderStatus(OrderStatus.valueOf(orderStatus));
            return orderRepository.save(order);

        }
        throw new Exception("Invalid order status");
    }

    @Override
    public Order cancelOrder(Long orderId) throws Exception {
        Order order=findOrderById(orderId);
        orderRepository.delete(order);
        return order;

    }

    @Override
    public List<Order> getUserOrders(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception {
       List<Order> orders=orderRepository.findByRestaurantId(restaurantId);
       if(orders!=null){
           orders=orders.stream().filter(order->order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
       }
       return orders;
    }
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> order=orderRepository.findById(orderId);
        if(order.isEmpty()){
            throw new Exception("Order not found");
        }
        return order.get();

    }
}
