package com.offcn.sms.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleQueueConfig {

    //定义队列名
    private String simpleQueue = "dongyimai.sms.queue";

    @Bean
    public Queue simpleQueue(){
        return new Queue(simpleQueue);
    }
}
