package com.github.bikeholik.cloudera.sensor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sensor")
@Data
// TODO not needed ?
public class SensorProperties {
    private int sensorsCount;
}
