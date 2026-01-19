package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CreateOrderRequest;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request.getUserId());
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable String orderId) {
        return orderService.getOrder(orderId);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable String userId) {
        return orderService.getOrdersByUser(userId);
    }

    @PostMapping("/{orderId}/cancel")
    public String cancelOrder(@PathVariable String orderId) {
        return orderService.cancelOrder(orderId);
    }


}
