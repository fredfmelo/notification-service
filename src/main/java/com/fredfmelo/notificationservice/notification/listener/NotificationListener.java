package com.fredfmelo.notificationservice.notification.listener;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredfmelo.eventdrivencore.exception.TechnicalException;
import com.fredfmelo.eventdrivencore.idempotency.executor.IdempotentExecutor;
import com.fredfmelo.notificationservice.notification.event.InventoryReservedEvent;
import com.fredfmelo.notificationservice.notification.event.NotificationEventType;
import com.fredfmelo.notificationservice.notification.event.OrderCreatedEvent;
import com.fredfmelo.notificationservice.notification.event.PaymentApprovedEvent;
import com.fredfmelo.notificationservice.notification.event.PaymentRefundedEvent;
import com.fredfmelo.notificationservice.notification.service.NotificationService;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;
    private final IdempotentExecutor idempotentExecutor;

    @SqsListener("${aws.sqs.notification-queue}")
    public void consume(String message) {
        try {
            NotificationEventType eventType = extractEventType(message);

            switch (eventType) {
                case ORDER_CREATED -> {
                    OrderCreatedEvent event = objectMapper.readValue(message, OrderCreatedEvent.class);

                    idempotentExecutor.execute(event, () -> notificationService.notifyOrderCreated(event));
                }

                case PAYMENT_APPROVED -> {
                    PaymentApprovedEvent event = objectMapper.readValue(message, PaymentApprovedEvent.class);

                    idempotentExecutor.execute(event, () -> notificationService.notifyPaymentApproved(event));
                }

                case PAYMENT_REFUNDED -> {
                    PaymentRefundedEvent event = objectMapper.readValue(message, PaymentRefundedEvent.class);

                    idempotentExecutor.execute(event, () -> notificationService.notifyPaymentRefunded(event));
                }

                case INVENTORY_RESERVED -> {
                    InventoryReservedEvent event = objectMapper.readValue(message, InventoryReservedEvent.class);

                    idempotentExecutor.execute(event, () -> notificationService.notifyInventoryReserved(event));
                }
            }

        } catch (JsonProcessingException ex) {
            throw new TechnicalException("Error processing notification message", ex);
        }
    }

    private NotificationEventType extractEventType(String message) throws JsonProcessingException {
        String eventType = objectMapper.readTree(message)
                .get("eventType")
                .asText();

        return NotificationEventType.fromValue(eventType)
                .orElseThrow(() -> new IllegalArgumentException("Unsupported eventType=" + eventType));
    }
}