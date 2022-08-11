package com.edu.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeginLogConfig {
    //配置日志bean的注入
    @Bean
    public Logger.Level getFeignlogger(){
        return Logger.Level.FULL;
    }
}
