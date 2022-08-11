package com.offcn.sms.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SendController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * url: http://localhost:9006/send
     * 用于测试短信是否可以发送成功，自测短信发送服务
     */
    @RequestMapping("/send")
    public void send(){
        Map<String, String> map = new HashMap<>();
        map.put("phone","13488162450");
        map.put("code","8888");
        rabbitTemplate.convertAndSend("dongyimai.sms.queue",map);
    }
}
