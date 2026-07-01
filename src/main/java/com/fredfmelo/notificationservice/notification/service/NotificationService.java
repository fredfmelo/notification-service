package com.fredfmelo.notificationservice.notification.service;

import org.springframework.stereotype.Service;

import com.fredfmelo.notificationservice.config.ServiceConfig;
import com.fredfmelo.notificationservice.notification.event.InventoryReservedEvent;
import com.fredfmelo.notificationservice.notification.event.OrderCreatedEvent;
import com.fredfmelo.notificationservice.notification.event.PaymentApprovedEvent;
import com.fredfmelo.notificationservice.notification.event.PaymentRefundedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final WhatsappService whatsappService;
    private final ServiceConfig serviceConfig;

    public void notifyOrderCreated(OrderCreatedEvent event) {
        log.info("[EMAIL] Order created id={}", event.orderId());
    }

    public void notifyPaymentApproved(PaymentApprovedEvent event) {
        log.info("[EMAIL] Payment approved order={}", event.orderId());
    }

    public void notifyPaymentRefunded(PaymentRefundedEvent event) {
        log.info("[EMAIL] Payment refunded order={} reason={}", event.orderId(), event.reason());
    }

    public void notifyInventoryReserved(InventoryReservedEvent event) {
        log.info("[EMAIL] Preparing shipment order={}", event.orderId());

        // todo: implement client base and send to the correct number
        try {
            whatsappService.send(String.format("Your order id %s was concluded!", event.orderId()),
                    serviceConfig.getTwilio().getRecipientNumber());
        } catch (Exception ex) {
            log.error("An error ocurred while trying to send whatsapp notification", ex);
        }
    }
}