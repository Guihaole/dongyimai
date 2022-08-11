package com.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class HelloConsumer01Main9002 {
    public static void main(String[] args) {
        SpringApplication.run(HelloConsumer01Main9002.class,args);
    }

    //远程调用发送请求用的，和Eureka没有关系，
    //没有它也可以远程调用，同httpClient一样
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
