package com.offcn.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.offcn.content.dao")
@EnableSwagger2
public class ContentApplicationMain9004 {
    public static void main(String[] args) {
        SpringApplication.run(ContentApplicationMain9004.class,args);
    }
}
