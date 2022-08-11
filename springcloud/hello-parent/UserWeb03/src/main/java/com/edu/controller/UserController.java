package com.edu.controller;


import com.edu.fegin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    //注入的是com.edu.fegin下的远程调用接口
    @Autowired
    private UserService userService;
    //查询全部用户数据，显示列表
    @GetMapping("/testUser")
    public Map<String, Object> findAll(Model model){
        Map<String, Object> map = userService.findAll();
        return map;
    }
}
