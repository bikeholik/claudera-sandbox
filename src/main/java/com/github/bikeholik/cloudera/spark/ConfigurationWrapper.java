package com.github.bikeholik.cloudera.spark;

import org.apache.hadoop.mapred.JobConf;

import java.io.Serializable;

class ConfigurationWrapper extends JobConf implements Serializable {
    ConfigurationWrapper(JobConf other) {
        super(other);
    }
}
