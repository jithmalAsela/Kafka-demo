package com.lk.notification.service;

import com.lk.notification.dto.Order;
import com.lk.notification.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    public void sendOrderConfirmation(OrderEvent orderEvent) {
        Order order = orderEvent.getOrder();

        // Simulate sending email
        log.info("========== EMAIL SENT ==========");
        log.info("To: {}", order.getCustomerEmail());
        log.info("Subject: Order Confirmation - {}", order.getOrderId());
        log.info("Body: Your order for {} x{} has been {}",
                order.getProductName(),
                order.getQuantity(),
                orderEvent.getEventType().toLowerCase());
        log.info("Total amount: ${}", order.getPrice() * order.getQuantity());
        log.info("==================================");
    }
}