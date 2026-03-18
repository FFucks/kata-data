package com.example.processor.config;

import com.example.shared.Sale;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
@EnableKafkaStreams
public class StreamConfig {

    @Bean
    public KStream<String, Sale> kStream(StreamsBuilder builder) {

        JsonSerde<Sale> saleSerde = new JsonSerde<>(Sale.class);

        ObjectMapper mapper = new ObjectMapper();

        KStream<String, String> rawStream = builder.stream(
                java.util.Arrays.asList("file-sales", "db-sales_source"),
                Consumed.with(Serdes.String(), Serdes.String())
        );

        KStream<String, Sale> stream = rawStream.mapValues(value -> {
            try {
                return mapper.readValue(value, Sale.class);
            } catch (Exception e) {
                System.out.println("Erro ao converter: " + value);
                return null;
            }
        }).filter((k, v) -> v != null);

        stream.groupBy((key, value) -> value.getCity(),
                        Grouped.with(Serdes.String(), saleSerde))
                .count()
                .toStream()
                .to("top-sales-city",
                        Produced.with(Serdes.String(), Serdes.Long()));

        stream.groupBy((key, value) -> value.getSalesman(),
                        Grouped.with(Serdes.String(), saleSerde))
                .count()
                .toStream()
                .to("top-salesman",
                        Produced.with(Serdes.String(), Serdes.Long()));

        return stream;
    }
}
