package com.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HelloServiceImpl implements HelloService{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 通过服务查询工具discoveryClient在Eureka中根据服务名查询服务列表
     * 选择一个url发送请求.
     * 实际这里可以通过负载均衡算法
     * 对List<ServiceInstance> instances做一个策略，也是负载均衡实现的基础
     * @return
     */
    public String getServerInfo(){
        //这里的服务名我们尽量使用大写，提高开发效率,直接从服务列表查到返回即可
        List<ServiceInstance> instances = discoveryClient.getInstances("HELLOPROVIDER01");
        if (instances!=null&&instances.size()>0) {
           ServiceInstance serviceInstance = instances.get(0);
           //获取对应服务的主机地址
           String host = serviceInstance.getHost();
           //获取对应服务端口号
           int port = serviceInstance.getPort();
           return "http://"+host+":"+port;
        }
        return null;
    }

    @Override
    public String sayHello() {
        ResponseEntity<String> entity = restTemplate.getForEntity(getServerInfo()+"/hello", String.class);
        return entity.getBody();
    }
}
