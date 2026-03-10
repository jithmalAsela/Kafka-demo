package com.lk.notification.consumer;

import com.lk.notification.dto.OrderEvent;
import com.lk.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void consumeOrderEvent(OrderEvent orderEvent) {
        log.info("Received order event: {}", orderEvent.getEventId());
        log.info("Event type: {}", orderEvent.getEventType());
        log.info("Order ID: {}", orderEvent.getOrder().getOrderId());

        // Process based on event type
        switch (orderEvent.getEventType()) {
            case "CREATED":
                emailService.sendOrderConfirmation(orderEvent);
                break;
            case "UPDATED":
                log.info("Order updated - no email sent for updates");
                break;
            case "CANCELLED":
                log.info("Order cancelled - no email sent for cancellations");
                break;
            default:
                log.warn("Unknown event type: {}", orderEvent.getEventType());
        }
    }
}