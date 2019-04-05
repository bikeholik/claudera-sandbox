package com.github.bikeholik.cloudera.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Location implements Serializable {
    private double latitude;
    private double longitude;
}
