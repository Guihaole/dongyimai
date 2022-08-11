package com.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServer1Main10086 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer1Main10086.class,args);
    }
}
