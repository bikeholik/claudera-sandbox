#!/usr/bin/env bash

sudo docker exec kafka kafka-console-consumer --bootstrap-server kafka:9092 --topic sensor-events
