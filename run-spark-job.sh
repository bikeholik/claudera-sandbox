#!/usr/bin/env bash

spark-submit --master yarn --deploy-mode client --class com.github.bikeholik.claudera.spark.Test /apps/spark-app.jar
