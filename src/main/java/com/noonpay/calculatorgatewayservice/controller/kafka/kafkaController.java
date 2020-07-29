package com.noonpay.calculatorgatewayservice.controller.kafka;

import com.noonpay.calculatorgatewayservice.service.kafka.ProducerService;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
public class kafkaController {
    private final ProducerService producerService;

    @Autowired
    public kafkaController(ProducerService producer) {
        this.producerService = producer;
    }

    @PostMapping(value = "/publish")
    public String sendMessageToKafkaTopic(@RequestParam("message") String message) {
        this.producerService.sendMessage(message);
        return "message sent";
    }
}
