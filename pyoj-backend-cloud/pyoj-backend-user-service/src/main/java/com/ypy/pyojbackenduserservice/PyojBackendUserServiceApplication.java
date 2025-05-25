package com.ypy.pyojbackenduserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ypy")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.ypy.pyojbackendcommon.feignclient"})
public class PyojBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PyojBackendUserServiceApplication.class, args);
    }
}
