package com.example.consumer.service;

import com.example.consumer.entity.TopCity;
import com.example.consumer.repository.TopCityRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final TopCityRepository repository;

    public KafkaConsumerService(TopCityRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "top-sales-city")
    public void consume(String data) {
        TopCity entity = new TopCity();
        entity.setCity(data);
        entity.setTotal(1L);

        repository.save(entity);
    }
}
