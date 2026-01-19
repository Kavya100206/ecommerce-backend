package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AddToCartRequest;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public CartItem addToCart(@RequestBody AddToCartRequest request) {
        return cartService.addToCart(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity()
        );
    }

    @GetMapping("/{userId}")
    public List<CartItem> getCart(@PathVariable String userId) {
        return cartService.getCartByUser(userId);
    }

    @DeleteMapping("/{userId}/clear")
    public String clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return "Cart cleared successfully";
    }
}
