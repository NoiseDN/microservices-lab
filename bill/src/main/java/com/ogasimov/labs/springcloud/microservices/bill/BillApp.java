package com.ogasimov.labs.springcloud.microservices.bill;

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
public class BillApp {
    public static void main(String[] args) {
        SpringApplication.run(BillApp.class, args);
    }
}
