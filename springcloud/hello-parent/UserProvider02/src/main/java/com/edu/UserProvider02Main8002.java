package com.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserProvider02Main8002 {
    public static void main(String[] args) {
        SpringApplication.run(UserProvider02Main8002.class,args);
    }
}
