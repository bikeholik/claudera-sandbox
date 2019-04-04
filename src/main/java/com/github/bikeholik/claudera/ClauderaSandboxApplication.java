package com.github.bikeholik.claudera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClauderaSandboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClauderaSandboxApplication.class, args);
    }

}
