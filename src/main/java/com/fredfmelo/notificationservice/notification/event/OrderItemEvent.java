package com.fredfmelo.notificationservice.notification.event;

import java.util.UUID;

public record OrderItemEvent(
                UUID productId,
                Integer quantity) {
}