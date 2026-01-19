package com.example.ecommerce.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "payments")
@Data
public class Payment {

    @Id
    private String id;

    private String orderId;
    private Double amount;
    private String status; // PENDING, SUCCESS, FAILED
    private String paymentId;
    private Instant createdAt;
}
