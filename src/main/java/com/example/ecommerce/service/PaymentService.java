package com.example.ecommerce.service;

import com.example.ecommerce.dto.PaymentWebhookRequest;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Payment;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Payment createPayment(String orderId, Double amount) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getStatus().equals("CREATED")) {
            throw new RuntimeException("Payment already initiated or order invalid");
        }

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setStatus("PENDING");
        payment.setPaymentId("pay_mock_" + UUID.randomUUID());
        payment.setCreatedAt(Instant.now());

        return paymentRepository.save(payment);
    }

    public void handleWebhook(PaymentWebhookRequest request) {

        Payment payment = paymentRepository.findAll().stream()
                .filter(p -> p.getOrderId().equals(request.getOrderId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(request.getStatus());
        paymentRepository.save(payment);

        Order order = orderRepository.findById(request.getOrderId()).get();
        order.setStatus(
                request.getStatus().equals("SUCCESS") ? "PAID" : "FAILED"
        );

        orderRepository.save(order);
    }
}
