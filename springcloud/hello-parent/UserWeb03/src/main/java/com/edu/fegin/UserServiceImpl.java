package com.edu.fegin;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//fegin降级的方法
@Service
public class UserServiceImpl implements UserService{
    @Override
    public Map<String, Object> findAll() {
        Map<String,Object> map=new HashMap<>();
        map.put("list",new ArrayList<>());
        map.put("version","调用远程服务失败,熔断被触发!");
        return map;
    }
}
