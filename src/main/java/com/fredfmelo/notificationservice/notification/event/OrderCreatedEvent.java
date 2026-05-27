package com.fredfmelo.notificationservice.notification.event;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fredfmelo.notificationservice.idempotency.event.IdempotentEvent;

public record OrderCreatedEvent(
        UUID eventId,
        String eventType,
        Instant occurredAt,
        String orderId,
        UUID customerId,
        List<OrderItemEvent> items
) implements IdempotentEvent {
}