package com.example.producer;

import com.example.shared.Sale;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class CsvLoader implements CommandLineRunner {

    private final KafkaTemplate<String, Sale> kafkaTemplate;

    public CsvLoader(KafkaTemplate<String, Sale> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        List<String> lines = Files.readAllLines(Path.of("data/sales.csv"));

        for (String line : lines) {
            String[] parts = line.split(",");

            Sale sale = new Sale();
            sale.setCity(parts[0]);
            sale.setSalesman(parts[1]);
            sale.setAmount(Double.parseDouble(parts[2]));

            kafkaTemplate.send("file-sales", sale);
        }
    }
}
