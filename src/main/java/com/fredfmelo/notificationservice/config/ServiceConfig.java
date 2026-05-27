package com.fredfmelo.notificationservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties
public class ServiceConfig {

    private Aws aws;
    private Twilio twilio;

    @Getter
    @Setter
    public static class Aws {
        private DynamoDb dynamodb;
        private Sns sns;
        private Sqs sqs;
    }

    @Getter
    @Setter
    public static class DynamoDb {
        private String tableName;
    }

    @Getter
    @Setter
    public static class Sns {
        private String topicArn;
    }

    @Getter
    @Setter
    public static class Sqs {
        private String notificationQueue;
    }

    @Getter
    @Setter
    public static class Twilio {
        private String accountSid;
        private String authToken;
        private String senderNumber;
        private String recipientNumber;
    }
}