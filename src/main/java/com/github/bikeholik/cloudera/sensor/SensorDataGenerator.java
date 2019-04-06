package com.github.bikeholik.cloudera.sensor;

import com.github.bikeholik.cloudera.common.Location;
import com.github.bikeholik.cloudera.common.SensorData;
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
                .temperature(getTemperature(random))
                .timestamp(now)
                .location(Location.builder()
                        .latitude(getCoordinate(random))
                        .longitude(getCoordinate(random))
                        .build())
                .build();
        log.debug("operation=send deviceId={} data={}", id, data);
        kafkaTemplate.send("sensor-events", String.valueOf(now), data);
    }

    private double getCoordinate(ThreadLocalRandom random) {
        return random.nextDouble(180d);
    }

    private int getTemperature(ThreadLocalRandom random) {
        return random.nextInt(71) - 10;
    }

}
