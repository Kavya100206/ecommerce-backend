package com.example.ecommerce.service;

import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public CartItem addToCart(String userId, String productId, Integer quantity) {

        // Validate product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if item already in cart
        CartItem existingItem = cartRepository.findByUserId(userId)
                .stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            return cartRepository.save(existingItem);
        }

        CartItem cartItem = new CartItem();
        cartItem.setUserId(userId);
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);

        return cartRepository.save(cartItem);
    }

    public List<CartItem> getCartByUser(String userId) {
        return cartRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
        List<CartItem> items = cartRepository.findByUserId(userId);
        cartRepository.deleteAll(items);
    }
}
