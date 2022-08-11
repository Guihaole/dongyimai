package com.edu.fegin;

import com.edu.config.FeginLogConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

//fegin远程调用映射接口
@FeignClient(value = "USERPROVIDER",
        configuration = FeginLogConfig.class,
        fallback = UserServiceImpl.class)
public interface UserService {
    //获取全部用户数据
    @GetMapping("/user")
    public Map<String, Object> findAll();
}
