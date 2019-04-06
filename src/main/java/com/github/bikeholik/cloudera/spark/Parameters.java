package com.github.bikeholik.cloudera.spark;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Parameters implements Serializable {
    private final String zookeeperHosts;
    private final int partitionsCount;

    static Parameters from(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException();
        }
        return new Parameters(args[0], Integer.parseInt(args[1]));
    }
}
