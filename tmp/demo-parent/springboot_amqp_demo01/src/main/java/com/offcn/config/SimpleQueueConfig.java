package com.offcn.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleQueueConfig {
    //定义队列
    private String simpleQueue = "spring.test.queue";
    @Bean
    public Queue simpleQueue(){
        return new Queue(simpleQueue);
    }
    /**
     * 定义队列
     */
    private final String work = "work_queue";

    @Bean
    public Queue workQueue() {
        return new Queue(work);
    }
}
