package com.edu.controller;

import com.edu.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloControllerConsumer {
    @Autowired
    private HelloService helloService;

    @RequestMapping("/testHello")
    public String sayHello(){
        return helloService.sayHello();
    }
}
