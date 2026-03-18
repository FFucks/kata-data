package com.example.processor.config;

import com.example.shared.Sale;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Configuration
@EnableKafkaStreams
public class StreamConfig {

    @Bean
    public KStream<String, Sale> kStream(StreamsBuilder builder) {

        KStream<String, Sale> stream = builder.stream("sales-topic");

        stream.groupBy((key, value) -> value.getCity())
                .count()
                .toStream()
                .to("top-sales-city");

        stream.groupBy((key, value) -> value.getSalesman())
                .count()
                .toStream()
                .to("top-salesman");

        return stream;
    }
}
