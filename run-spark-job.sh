#!/usr/bin/env bash

spark-submit --master yarn --deploy-mode client --class com.github.bikeholik.claudera.spark.HBaseSinkApplication /apps/spark-app.jar
