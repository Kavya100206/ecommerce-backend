package com.example.ecommerce.webhook;

import com.example.ecommerce.dto.PaymentWebhookRequest;
import com.example.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks/payment")
public class PaymentWebhookController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public String handleWebhook(@RequestBody PaymentWebhookRequest request) {
        paymentService.handleWebhook(request);
        return "Webhook processed";
    }
}
