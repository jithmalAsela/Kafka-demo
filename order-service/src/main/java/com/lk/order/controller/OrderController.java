package com.lk.order.controller;

import com.lk.order.dto.Order;
import com.lk.order.dto.OrderEvent;
import com.lk.order.service.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProducer orderProducer;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        // Generate order ID if not provided
        if (order.getOrderId() == null) {
            order.setOrderId(UUID.randomUUID().toString());
        }

        // Create order event
        OrderEvent event = new OrderEvent(
                UUID.randomUUID().toString(),
                "CREATED",
                order,
                System.currentTimeMillis()
        );

        // Send to Kafka
        orderProducer.sendOrderEvent(event);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Order created successfully: " + order.getOrderId());
    }
}