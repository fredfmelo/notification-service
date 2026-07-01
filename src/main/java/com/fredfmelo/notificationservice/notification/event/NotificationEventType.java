package com.fredfmelo.notificationservice.notification.event;

import java.util.Arrays;
import java.util.Optional;

public enum NotificationEventType {

    ORDER_CREATED("ORDER_CREATED"),
    PAYMENT_APPROVED("PAYMENT_APPROVED"),
    PAYMENT_REFUNDED("PAYMENT_REFUNDED"),
    INVENTORY_RESERVED("INVENTORY_RESERVED");

    private final String value;

    NotificationEventType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Optional<NotificationEventType> fromValue(String value) {
        return Arrays.stream(values())
                .filter(type -> type.value.equals(value))
                .findFirst();
    }
}
