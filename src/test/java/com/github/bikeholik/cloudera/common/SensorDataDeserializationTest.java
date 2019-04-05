package com.github.bikeholik.cloudera.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

public class SensorDataDeserializationTest {
    @Test
    public void deserialize() throws Exception {
        String json = "{\"data\":{\"deviceId\":\"a9c83256-b96a-4115-95f0-25cc94f28896\",\"temperature\":16,\"location\":{\"latitude\":66.60910446822467,\"longitude\":115.32225152975992},\"timestamp\":1554402023}}";
        ObjectMapper objectMapper = ObjectMapperSupport.createObjectMapper();
        SensorData data = objectMapper.readValue(json, SensorData.class);
        Assert.assertNotNull(data);
    }
}
