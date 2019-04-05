package com.github.bikeholik.claudera.common;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonRootName("data")
public class SensorData implements Serializable {
    private String deviceId;
    private int temperature;
    private Location location;
    private long timestamp;
}
