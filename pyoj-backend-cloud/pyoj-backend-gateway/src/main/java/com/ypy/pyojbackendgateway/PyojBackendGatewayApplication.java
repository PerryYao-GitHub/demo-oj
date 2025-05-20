package com.ypy.pyojbackendgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ypy")
@EnableDiscoveryClient
public class PyojBackendGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PyojBackendGatewayApplication.class, args);
    }

}
