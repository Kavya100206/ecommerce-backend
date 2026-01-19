package com.example.ecommerce.service;

import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order createOrder(String userId) {

        List<CartItem> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double totalAmount = 0;

        // Calculate total & check stock
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for " + product.getName());
            }

            totalAmount += product.getPrice() * item.getQuantity();
        }

        // Create order
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus("CREATED");
        order.setCreatedAt(Instant.now());

        order = orderRepository.save(order);

        // Create order items & update stock
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId()).get();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(product.getId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());

            orderItemRepository.save(orderItem);

            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        // Clear cart
        cartRepository.deleteAll(cartItems);

        return order;
    }

    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getOrdersByUser(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public String cancelOrder(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getStatus().equals("CREATED")) {
            throw new RuntimeException("Order cannot be cancelled");
        }

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

        for (OrderItem item : orderItems) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus("CANCELLED");
        orderRepository.save(order);

        return "Order cancelled successfully";
    }


}
