package com.edu.service.impl;

import com.edu.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello() {
        return "hello Eureka!";
    }
}
