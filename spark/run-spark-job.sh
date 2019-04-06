#!/usr/bin/env bash

sudo docker exec quickstart.cloudera spark-submit --master yarn --deploy-mode client --class com.github.bikeholik.cloudera.spark.SensorEventsHBaseSink /apps/spark-app.jar zookeepr:2181 1
