package com.github.bikeholik.claudera.spark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bikeholik.claudera.common.ObjectMapperSupport;
import com.github.bikeholik.claudera.common.SensorData;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapred.TableOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import static org.apache.hadoop.hbase.mapred.TableOutputFormat.OUTPUT_TABLE;
import static org.apache.hadoop.hbase.util.Bytes.toBytes;

public class HBaseSinkApplication {

    private static final String SENSOR_DATA_TABLE = "sensor";
    private static final byte[] COLUMN_FAMILY = toBytes("data");
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        }
    };

    public static void main(String[] args) {

        final ObjectMapper objectMapper = ObjectMapperSupport.createObjectMapper();
        final Configuration configuration = HBaseConfiguration.create();
        configuration.set(OUTPUT_TABLE, SENSOR_DATA_TABLE);
        final JobConf jobConf = new JobConf(configuration, HBaseSinkApplication.class);
        jobConf.setOutputFormat(TableOutputFormat.class);
        jobConf.set("mapreduce.output.fileoutputformat.outputdir", "/user/sensor/out");
        jobConf.set(OUTPUT_TABLE, SENSOR_DATA_TABLE);
        final ConfigurationWrapper conf = new ConfigurationWrapper(jobConf);

        JavaStreamingContext ssc = new JavaStreamingContext(new JavaSparkContext(), Durations.seconds(60));

        JavaPairReceiverInputDStream<String, String> stream = KafkaUtils.createStream(ssc, "zookeeper:2181", "sensor-data-hbase-sink", Collections.singletonMap("sensor-events", 1));

//        stream.print(2);

        stream.foreachRDD(new VoidFunction<JavaPairRDD<String, String>>() {
            @Override
            public void call(JavaPairRDD<String, String> rdd) throws Exception {

                rdd
                        .mapToPair(new PairFunction<Tuple2<String, String>, ImmutableBytesWritable, Put>() {

                            @Override
                            public Tuple2<ImmutableBytesWritable, Put> call(Tuple2<String, String> tuple) throws Exception {
                                SensorData sensorData = objectMapper.readValue(tuple._2(), SensorData.class);
                                return new Tuple2<>(
                                        new ImmutableBytesWritable(rowId(sensorData)),
                                        convert(tuple._2(), sensorData)
                                );
                            }
                        })
                        .saveAsHadoopDataset(conf);
            }
        });

        ssc.start();
        ssc.awaitTermination();
    }

    static Put convert(String raw, SensorData sensorData) {
        SimpleDateFormat dateFormat = getSimpleDateFormat();
        Put put = new Put(rowId(sensorData));
        put.addColumn(COLUMN_FAMILY, toBytes("timestamp"), toBytes(dateFormat.format(new Date((sensorData.getTimestamp())))));
        put.addColumn(COLUMN_FAMILY, toBytes("temperature"), toBytes(sensorData.getTemperature()));
        put.addColumn(COLUMN_FAMILY, toBytes("raw"), toBytes(raw));
        put.addColumn(COLUMN_FAMILY, toBytes("device"), toBytes(sensorData.getDeviceId()));
        return put;
    }

    private static byte[] rowId(SensorData sensorData) {
        return toBytes(sensorData.getDeviceId() + "-" + sensorData.getTimestamp());
    }

    private static SimpleDateFormat getSimpleDateFormat() {
        return DATE_FORMAT.get();
    }
}
