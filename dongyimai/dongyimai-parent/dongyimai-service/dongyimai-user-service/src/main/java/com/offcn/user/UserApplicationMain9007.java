package com.offcn.user;

import com.offcn.utils.TokenDecode;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.offcn.user.dao")
public class UserApplicationMain9007 {
    public static void main(String[] args) {
        SpringApplication.run(UserApplicationMain9007.class,args);
    }
    @Bean
    public TokenDecode tokenDecode(){
        return new TokenDecode();
    }
}
