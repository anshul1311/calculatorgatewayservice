package com.noonpay.calculatorgatewayservice.service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    private static final String TOPIC = "email-queue";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    public void sendMessage(String message) {
       // logger.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(TOPIC, message);
    }
}
