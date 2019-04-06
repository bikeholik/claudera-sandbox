package com.github.bikeholik.cloudera.sensor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Profile("!local")
@Configuration
class SensorConfiguration {
    @Bean
    SensorRegistrar sensorRegistrar(Environment environment){
        return new SensorRegistrar(environment.getRequiredProperty("sensor.sensors-count", Integer.class));
    }
}
