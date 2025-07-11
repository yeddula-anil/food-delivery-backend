package org.example.food.service;

import org.example.food.model.Cart;
import org.example.food.model.CartItem;
import org.example.food.model.Food;
import org.example.food.model.User;
import org.example.food.repository.CartItemRepository;
import org.example.food.repository.CartRepository;
import org.example.food.repository.UserRepository;
import org.example.food.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartItem addToCart(AddCartItemRequest req, String jwt) throws Exception {
       User user=userService.findByJwtToken(jwt);
       Food food=foodService.findFoodById(req.getFoodId());
       Cart cart=cartRepository.findByUser_Id(user.getId());
       for(CartItem cartItem:cart.getItems()){
           if(cartItem.getFood().equals(food)){
               int newQuantity=cartItem.getQuantity()+req.getQuantity();
               return updateCartItemQuantity(cartItem.getId(),newQuantity);
           }
       }
       CartItem cartItem=new CartItem();
       cartItem.setFood(food);
       cartItem.setQuantity(req.getQuantity());
       cartItem.setQuantity(req.getQuantity());
       cartItem.setIngredients(req.getIngredients());
       cartItem.setTotalprice(req.getQuantity()*food.getPrice());
       CartItem savedItem=cartItemRepository.save(cartItem);
       cart.getItems().add(savedItem);
       return savedItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItem=cartItemRepository.findById(cartItemId);
        if (cartItem.isEmpty()) {
            throw new Exception("CartItem not found");
        }
        CartItem item=cartItem.get();
        item.setQuantity(quantity);
        item.setTotalprice(item.getFood().getPrice()*quantity);
        CartItem savedItem=cartItemRepository.save(item);
        return savedItem;

    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        Cart cart=cartRepository.findByUser_Id(user.getId());
        Optional<CartItem> cartItem=cartItemRepository.findById(cartItemId);
        if (cartItem.isEmpty()) {
            throw new Exception("CartItem not found");
        }
        CartItem item=cartItem.get();
        cart.getItems().remove(item);
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {
        Long total=0L;
        for(CartItem cartItem:cart.getItems()){
            total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
        }
        return total;

    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> cart=cartRepository.findById(id);
        if (cart.isEmpty()) {
            throw new Exception("Cart not found");
        }
        return cart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
//        User user=userService.findByJwtToken(jwt);
        Cart cart=cartRepository.findByUser_Id(userId);
        cart.setTotal(calculateCartTotal(cart));
        return cart;
    }

    @Override

    public Cart clearCart(Long UserId) throws Exception {
        Cart cart=cartRepository.findByUser_Id(UserId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }

}
