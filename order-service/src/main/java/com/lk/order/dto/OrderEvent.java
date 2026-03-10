package com.lk.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private String eventId;
    private String eventType; // CREATED, UPDATED, CANCELLED
    private Order order;
    private long timestamp;
}