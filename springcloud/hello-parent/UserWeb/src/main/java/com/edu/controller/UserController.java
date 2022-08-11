package com.edu.controller;

import com.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    //查询全部用户数据，显示列表
    @GetMapping("/testUser")
    public Map<String, Object> findAll(Model model){
        Map<String, Object> map = userService.findAll();
        model.addAttribute("page",map.get("list"));
        //获取服务提供者信息
        model.addAttribute("version",map.get("version"));
        return map;
    }
}
