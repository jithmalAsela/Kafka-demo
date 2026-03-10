package com.lk.order.service;

import com.lk.order.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private static final String TOPIC = "order-events";

    public void sendOrderEvent(OrderEvent orderEvent) {
        CompletableFuture<SendResult<String, OrderEvent>> future =
                kafkaTemplate.send(TOPIC, orderEvent.getOrder().getOrderId(), orderEvent);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Order event sent successfully: {}, partition: {}",
                        orderEvent.getEventId(), result.getRecordMetadata().partition());
            } else {
                log.error("Failed to send order event: {}", orderEvent.getEventId(), ex);
            }
        });
    }
}