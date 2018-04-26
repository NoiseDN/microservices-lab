package com.ogasimov.labs.springcloud.microservices.table;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@EnableBinding(Sink.class)
public class TableApp {
    public static void main(String[] args) {
        SpringApplication.run(TableApp.class, args);
    }
}
