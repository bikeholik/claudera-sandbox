#!/usr/bin/env bash

spark-submit --master yarn --deploy-mode client --class com.github.bikeholik.cloudera.spark.SensorEventsHBaseSink /apps/spark-app.jar
