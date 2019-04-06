#!/usr/bin/env bash

# start services

echo "Start cloudera services"

/scripts/docker-quickstart.sh

# setup sandbox

if [[ ! -f /tmp/.init ]]; then

    sleep 30

    echo "Create hbase table"

    hbase shell /scripts/hbase-commands.txt

    echo "Create hive table"

    hive -f /scripts/create_sensor_data_table.sql

    impala-shell -q 'invalidate metadata sensor_data'

    touch /tmp/.init

fi

exec bash
