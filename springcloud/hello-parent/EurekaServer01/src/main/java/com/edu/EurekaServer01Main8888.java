package com.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServer01Main8888 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer01Main8888.class,args);
    }
}
