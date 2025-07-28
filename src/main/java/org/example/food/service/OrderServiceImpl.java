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
    public List<OrderItem> createOrder(OrderRequest req, User user) throws Exception {
        Adress shippingAdress=req.getDeliveryAdress();
        Adress savedAdress=adressRepository.save(shippingAdress);
        if(!user.getAdresses().contains(savedAdress)){
            user.getAdresses().add(savedAdress);
            userRepository.save(user);
        }


        Cart cart=cartService.findCartByUserId(user.getId());
        List<OrderItem> orderItems=new ArrayList<OrderItem>();
        for(CartItem cartItem:cart.getItems()){
            Restaurant restaurant=restaurantService.findRestaurantById(cartItem.getFood().getRestaurant().getId());
            if(restaurant==null){
                throw new Exception("Restaurant not found");
            }
            OrderItem order=new OrderItem();
            order.setFood(cartItem.getFood());
            order.setUser(cart.getUser());
            order.setQuantity(cartItem.getQuantity());

            order.setTotalPrice(cartItem.getFood().getPrice()*cartItem.getQuantity());
            order.setRestaurant(restaurant);
            order.setOrderStatus(OrderStatus.COMPLETED);
            order.setOrderDate(LocalDateTime.now());
            order.setIngredients(cartItem.getIngredients());
            OrderItem savedOrderItem=orderItemRepository.save(order);
            orderItems.add(savedOrderItem);
            restaurant.getOrders().add(order);
            restaurantRepository.save(restaurant);

        }
        return orderItems;








    }

    @Override
    public OrderItem updateOrder(Long orderId, String orderStatus) throws Exception {
        OrderItem order=findOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED") || orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING")){
            order.setOrderStatus(OrderStatus.valueOf(orderStatus));
            return orderItemRepository.save(order);

        }
        throw new Exception("Invalid order status");
    }

    @Override
    public OrderItem cancelOrder(Long orderId) throws Exception {
        OrderItem order=findOrderById(orderId);
        orderItemRepository.delete(order);
        return order;

    }
    @Override
    public List<OrderItem> getUserOrders(Long userId) {
        return orderItemRepository.findByUserId(userId);
    }


    @Override
    public List<OrderItem> getRestaurantOrders(Long restaurantId) throws Exception {
       List<OrderItem> orders=orderItemRepository.findByRestaurantId(restaurantId);

       return orders;
    }
    public OrderItem findOrderById(Long orderId) throws Exception {
        Optional<OrderItem> order=orderItemRepository.findById(orderId);
        if(order.isEmpty()){
            throw new Exception("Order not found");
        }
        return order.get();

    }
}
