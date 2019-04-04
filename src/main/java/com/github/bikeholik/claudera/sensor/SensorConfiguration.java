package com.github.bikeholik.claudera.sensor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
class SensorConfiguration {
    @Bean
    SensorRegistrar sensorRegistrar(SensorProperties sensorProperties, Environment environment){
        return new SensorRegistrar(environment.getRequiredProperty("sensor.sensors-count", Integer.class));
    }
}
