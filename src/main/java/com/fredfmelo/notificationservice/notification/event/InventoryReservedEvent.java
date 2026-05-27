package com.fredfmelo.notificationservice.notification.event;

import java.time.Instant;
import java.util.UUID;

import com.fredfmelo.notificationservice.idempotency.event.IdempotentEvent;

public record InventoryReservedEvent(
        UUID eventId,
        String eventType,
        Instant occurredAt,
        String orderId
) implements IdempotentEvent {
}