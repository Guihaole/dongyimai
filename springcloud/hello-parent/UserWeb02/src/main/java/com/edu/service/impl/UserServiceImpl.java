package com.edu.service.impl;

import com.edu.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;   //rest接口调用工具

    //编写查询服务方法
    public String getServerUrl(){
        return "http://USERPROVIDER";
    }

    /**
     * HystrixCommand的意思是当你的调用这个方法超时了，
     * 就走指定的降级方法，默认超时位1000ms
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "findAllCallBack")
    public Map<String,Object> findAll() {
        String url=getServerUrl();
        Map<String,Object> map = restTemplate.getForObject(url + "/user", Map.class);
        return map;
    }

    //熔断触发后，回调方法
    public Map<String,Object> findAllCallBack() {
        Map<String,Object> map=new HashMap<>();
        map.put("list",new ArrayList<>());
        map.put("version","熔断被触发，远程调用失败");
        return map;
    }
}
