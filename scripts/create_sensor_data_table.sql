CREATE EXTERNAL TABLE sensor_data (
  id STRING,
  deviceId STRING,
  timestamp STRING,
  temperature INT
) STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
    WITH SERDEPROPERTIES ("hbase.columns.mapping" =  ":key,data:device,data:timestamp,data:temperature#b")
  TBLPROPERTIES("hbase.table.name" = "sensor");
