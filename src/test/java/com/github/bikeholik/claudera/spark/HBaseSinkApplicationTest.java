package com.github.bikeholik.claudera.spark;

import com.github.bikeholik.claudera.common.SensorData;
import org.apache.hadoop.hbase.client.Put;
import org.junit.Test;
import scala.Tuple2;

import static org.junit.Assert.assertNotNull;

public class HBaseSinkApplicationTest {

    @Test
    public void convert() {
        Put convert = HBaseSinkApplication.convert("test", SensorData.builder()
                .deviceId("d")
                .temperature(1)
                .timestamp(System.currentTimeMillis())
                .build());

        assertNotNull(convert);
    }
}
