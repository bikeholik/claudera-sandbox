package com.github.bikeholik.claudera.spark;

import org.apache.hadoop.mapred.JobConf;

import java.io.Serializable;

class ConfigurationWrapper extends JobConf implements Serializable {
    ConfigurationWrapper(JobConf other) {
        super(other);
    }
}
