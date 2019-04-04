package com.github.bikeholik.claudera.sensor;

import com.github.bikeholik.claudera.common.Location;
import com.github.bikeholik.claudera.common.SensorData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Slf4j
class SensorDataGenerator {
    private final UUID id;
    private final KafkaTemplate<String, SensorData> kafkaTemplate;

    @Scheduled(fixedRateString = "${sensor.frequency}")
    void generate() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long now = System.currentTimeMillis();
        SensorData data = SensorData.builder()
                .deviceId(id.toString())
                .temperature(random.nextInt(30))
                .timestamp(now / 1000)
                .location(Location.builder()
                        .latitude(random.nextDouble(180d))
                        .longitude(random.nextDouble(180d))
                        .build())
                .build();
        log.debug("operation=send deviceId={} data={}", id, data);
        kafkaTemplate.send("sensor-events", String.valueOf(now), data);
    }

}
