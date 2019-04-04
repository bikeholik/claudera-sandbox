package com.github.bikeholik.claudera.spark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bikeholik.claudera.common.ObjectMapperSupport;
import com.github.bikeholik.claudera.common.SensorData;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import java.util.Collections;

public class Test {
    public static void main(String[] args) {

        final ObjectMapper objectMapper = ObjectMapperSupport.createObjectMapper();

        JavaStreamingContext ssc = new JavaStreamingContext(new JavaSparkContext(), Durations.seconds(2));

        JavaPairDStream<String, SensorData> stream = KafkaUtils.createStream(ssc, "zookeeper:2181", "consumer-name", Collections.singletonMap("sensor-events", 1))
                .mapValues(new Function<String, SensorData>() {
                    @Override
                    public SensorData call(String v1) throws Exception {
                        return objectMapper.readValue(v1, SensorData.class);
                    }
                });

        stream.print(2);

        ssc.start();
        ssc.awaitTermination();
    }
}
