package com.fredfmelo.notificationservice.notification.service;

import org.springframework.stereotype.Service;

import com.fredfmelo.notificationservice.config.ServiceConfig;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WhatsappService {

    private static final String WHATSAPP_PREFIX = "whatsapp:";

    private final ServiceConfig config;

    @PostConstruct
    public void init() {
        Twilio.init(
                config.getTwilio().getAccountSid(),
                config.getTwilio().getAuthToken()
        );
    }

    public void send(String message, String clientNumber) {

        String recipient = clientNumber.startsWith(WHATSAPP_PREFIX)
                ? clientNumber
                : WHATSAPP_PREFIX + clientNumber;

        try {
            Message response = Message.creator(
                    new PhoneNumber(recipient),
                    new PhoneNumber(config.getTwilio().getSenderNumber()),
                    message
            ).create();

            log.info(
                    "Twilio sid={} status={} error={}",
                    response.getSid(),
                    response.getStatus(),
                    response.getErrorMessage()
            );

        } catch (ApiException ex) {
            log.error(
                    "Twilio error code={} message={}",
                    ex.getCode(),
                    ex.getMessage(),
                    ex
            );
        }
    }
}