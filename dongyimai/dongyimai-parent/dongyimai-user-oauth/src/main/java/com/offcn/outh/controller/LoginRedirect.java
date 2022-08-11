package com.offcn.outh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/oauth")
public class LoginRedirect {
    /***
     * 跳转到登录页面
     * @return
     */
    @GetMapping(value = "/login")
    public String login(@RequestParam(value = "from",required = false,defaultValue = "")
                        String from, Model model){
        //存储from
        model.addAttribute("from",from);
        return "login";
    }
}
