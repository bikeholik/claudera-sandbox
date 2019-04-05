package com.github.bikeholik.cloudera.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bikeholik.cloudera.common.ObjectMapperSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@RequiredArgsConstructor
class KafkaConfiguration {

    private final KafkaProperties kafkaProperties;

    @Bean
    ObjectMapper objectMapper() {
        return ObjectMapperSupport.createObjectMapper();
    }

    @Bean
    ProducerFactory<?, ?> defaultKafkaProducerFactory() {
        DefaultKafkaProducerFactory<Object, Object> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(objectMapper()));
        return kafkaProducerFactory;
    }
}
