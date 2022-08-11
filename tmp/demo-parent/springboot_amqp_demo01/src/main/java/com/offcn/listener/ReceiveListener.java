package com.offcn.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiveListener {

    @RabbitListener(queues = "spring.test.queue")
    public void recieve(String message) {
        System.out.println(message);
    }
    @RabbitListener(queues = "work_queue")
    public void recieveWork1(String message) {
        System.out.println("listener1:" + message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @RabbitListener(queues = "work_queue")
    public void recieveWork2(String message) {
        System.out.println("listener2:" + message);
    }
    @RabbitListener(queues = "fanout_queue_1")
    public void recieve1(String message) {
        System.out.println("listener1:" + message);
    }

    @RabbitListener(queues = "direct_queue_1")
    public void recieveDirect1(String message) {
        System.out.println("listener1:" + message);
    }

    @RabbitListener(queues = "direct_queue_2")
    public void recieveDirect2(String message) {
        System.out.println("listener2:" + message);

    }

    @RabbitListener(queues = "topic_queue_1")
    public void recieveTopic1(String message) {
        System.out.println("listener1:" + message);
    }

    @RabbitListener(queues = "topic_queue_2")
    public void recieveTopic2(String message) {
        System.out.println("listener2:" + message);
    }
}
