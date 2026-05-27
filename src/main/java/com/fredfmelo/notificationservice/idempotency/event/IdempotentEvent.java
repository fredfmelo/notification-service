package com.fredfmelo.notificationservice.idempotency.event;

import java.util.UUID;

public interface IdempotentEvent {
    UUID eventId();
}
