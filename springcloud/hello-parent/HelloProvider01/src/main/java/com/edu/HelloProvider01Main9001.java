package com.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HelloProvider01Main9001 {
    public static void main(String[] args) {
        SpringApplication.run(HelloProvider01Main9001.class,args);
    }
}
