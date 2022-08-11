package com.edu.service;

import com.edu.pojo.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService{
    @Override
    public Map<String, Object> findAll() {
        Map<String, Object> map = new HashMap<>();
        List<User> list=new ArrayList<User>();
        list.add(new User(1L,"zhangsan1",21));
        list.add(new User(2L,"zhangsan2",22));
        list.add(new User(3L,"zhangsan3",23));
        map.put("list",list);
        return map;
    }
}
