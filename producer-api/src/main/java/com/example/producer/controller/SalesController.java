package com.example.producer.controller;

import com.example.shared.Sale;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
public class SalesController {

    private final KafkaTemplate<String, Sale> kafkaTemplate;

    public SalesController(KafkaTemplate<String, Sale> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void send(@RequestBody Sale sale) {
        kafkaTemplate.send("sales-topic", sale);
    }
}
