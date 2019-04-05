package com.github.bikeholik.cloudera.spark;

import com.github.bikeholik.cloudera.common.SensorData;
import org.apache.hadoop.hbase.client.Put;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SensorEventsHBaseSinkTest {

    @Test
    public void convert() {
        Put convert = SensorEventsHBaseSink.convert("test", SensorData.builder()
                .deviceId("d")
                .temperature(1)
                .timestamp(System.currentTimeMillis())
                .build());

        assertNotNull(convert);
    }
}
