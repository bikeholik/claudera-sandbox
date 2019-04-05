package com.github.bikeholik.cloudera.query;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "queries")
@Data
public class QueryProperties {
    private String maxTemperaturePerDevice;
    private String maxTemperaturePerDeviceOnGivenDay;
    private String amountOfPointsPerDevice;

}
