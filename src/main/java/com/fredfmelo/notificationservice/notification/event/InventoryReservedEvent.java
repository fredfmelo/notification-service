package com.fredfmelo.notificationservice.notification.event;

import java.time.Instant;
import java.util.UUID;

import com.fredfmelo.eventdrivencore.event.Event;

public record InventoryReservedEvent(
                UUID eventId,
                String traceId,
                String eventType,
                Instant occurredAt,
                String orderId) implements Event {
}