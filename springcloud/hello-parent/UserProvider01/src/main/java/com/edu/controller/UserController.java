package com.edu.controller;

import com.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    //获取全部用户数据
    @GetMapping("/user")
    public Map<String, Object> findAll() {
        Map<String, Object> map = userService.findAll();
        map.put("version", "UserProvider01");
        //随机睡 0-1500毫秒
        try {
            int sleepCount = new Random().nextInt(1500);
            System.out.println(sleepCount);
            Thread.sleep(sleepCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return map;

    }
}
