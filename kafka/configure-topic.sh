#!/usr/bin/env bash

sudo docker exec kafka kafka-topics --zookeeper zookeeper:2181 --alter --topic sensor-events --config retention.ms=3600000
