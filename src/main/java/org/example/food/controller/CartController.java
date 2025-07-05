package org.example.food.controller;

import org.example.food.model.Cart;
import org.example.food.model.CartItem;
import org.example.food.model.User;
import org.example.food.request.AddCartItemRequest;
import org.example.food.request.UpdateCardItemRequest;
import org.example.food.service.CartService;
import org.example.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addCartItem(@RequestBody AddCartItemRequest req,
                                                @RequestHeader("Authorization") String jwt) throws Exception{
        CartItem item=cartService.addToCart(req,jwt);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCardItemQuantity(@RequestBody UpdateCardItemRequest req,
                                                           @RequestHeader("Authorization") String jwt) throws Exception{
        CartItem item=cartService.updateCartItemQuantity(req.getCarditemId(),req.getQuantity());
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long id,
                                               @RequestHeader("Authoriation") String jwt) throws Exception{
        Cart cart=cartService.removeItemFromCart(id, jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCartItem(@RequestHeader("Authorization") String jwt) throws Exception{
        User user=userService.findByJwtToken(jwt);
        Cart cart=cartService.clearCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        Cart cart=cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);


    }
}
