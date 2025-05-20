package com.ypy.pyojbackendsubmitservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ypy")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.ypy.pyojbackendserviceclient.service"})
public class PyojBackendSubmitServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PyojBackendSubmitServiceApplication.class, args);
    }
}
