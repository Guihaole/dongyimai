package com.edu.service.impl;

import com.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 可以通过负载均衡算法从Eureka中获取一个服务的端口和ip，默认是轮询
     * @return
     */
    public String getServerUrl() {
        //通过客户端调用服务均衡器查找服务
        ServiceInstance inst = loadBalancerClient.choose("USERPROVIDER");
        //获取服务提供者服务器ip、端口号
        String ip = inst.getHost();
        int port = inst.getPort();
        //拼接调用地址
        String url="http://"+ip+":"+port;
        return url;
    }
    @Override
    public Map<String, Object> findAll() {
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(getServerUrl()+"/user", Map.class);
        return forEntity.getBody();
    }
}
