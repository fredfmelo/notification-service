package com.fredfmelo.notificationservice.notification.listener;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredfmelo.notificationservice.notification.event.InventoryReservedEvent;
import com.fredfmelo.notificationservice.notification.event.OrderCreatedEvent;
import com.fredfmelo.notificationservice.notification.event.PaymentApprovedEvent;
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

    @SqsListener("${aws.sqs.notification-queue}")
    public void consume(String message) throws Exception {

        JsonNode node = objectMapper.readTree(message);

        String eventType = node.get("eventType").asText();

        switch (eventType) {

            case "ORDER_CREATED" -> {
                OrderCreatedEvent event =
                        objectMapper.treeToValue(
                                node,
                                OrderCreatedEvent.class
                        );

                notificationService.notifyOrderCreated(event);
            }

            case "PAYMENT_APPROVED" -> {
                PaymentApprovedEvent event =
                        objectMapper.treeToValue(
                                node,
                                PaymentApprovedEvent.class
                        );

                notificationService.notifyPaymentApproved(event);
            }

            case "INVENTORY_RESERVED" -> {
                InventoryReservedEvent event =
                        objectMapper.treeToValue(
                                node,
                                InventoryReservedEvent.class
                        );

                notificationService.notifyInventoryReserved(event);
            }

            default -> log.warn(
                    "Unsupported event type={}",
                    eventType
            );
        }
    }
}