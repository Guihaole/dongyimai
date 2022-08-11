package com.offcn.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //1.发送消息到队列
    @RequestMapping("/")
    public void send(){
        for (int i = 0; i <5 ; i++) {
            //指定路由键发送消息
            rabbitTemplate.convertAndSend("spring.test.queue", "简单队列消息"+i);
        }
    }
    @RequestMapping("/send2")
    public void send2() {
        for (int i = 0; i < 50; i++) {
            String message = "工作队列消息" + i;
            rabbitTemplate.convertAndSend("work_queue", message);
        }
    }
    //2.发送消息到广播交换机
    @RequestMapping("/send3")
    public void send3() {
        for (int i = 0; i < 5; i++) {
            String message = "订阅模式消息" + i;
            rabbitTemplate.convertAndSend("fanoutExchange", "", message);
        }
    }
    //3.发送消息到路由交换机
    @RequestMapping("/send01")
    public void sendMessageA() {
        for (int i = 0; i < 5; i++) {
            String message = "路由模式--routingKey=update消息" + i;
            System.out.println("我是生产信息的：" + message);
            rabbitTemplate.convertAndSend("directExchange", "update", message);
        }
    }
    @RequestMapping("/send02")
    public void sendMessageB() {
        for (int i = 0; i < 5; i++) {
            String message = "路由模式--routingKey=add消息" + i;
            System.out.println("我是生产信息的：" + message);
            rabbitTemplate.convertAndSend("directExchange", "add", message);
        }
    }


    //4.发送消息到topic交换机
    @RequestMapping("/sendTopic01")
    public void send01() {
        for (int i = 0; i < 5; i++) {
            String message = "通配符模式--routingKey=topic.keyA消息" + i;
            System.out.println("我是生产信息的：" + message);
            rabbitTemplate.convertAndSend("topicExchange", "topic.keyA", message);
        }
    }
    @RequestMapping("/sendTopic02")
    public void send02(){
        for (int i = 0; i < 5; i++) {
            String message = "通配符模式--routingKey=topic.#消息" + i;
            System.out.println("我是生产信息的：" + message);
            rabbitTemplate.convertAndSend("topicExchange", "topic.keyD.keyE", message);
        }
    }
}
