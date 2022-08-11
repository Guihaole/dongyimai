package com.offcn.user.fegin;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;

import com.offcn.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/****
 * @Author:ujiuye
 * @Description:
 * @Date 2021/2/1 14:19
 *****/
@FeignClient(name="user",path = "/user")
public interface UserFeign {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @GetMapping("/load/{username}")
    public Result<User> findByUsername(@PathVariable(value = "username") String username);

    /**
     * 增加积分远程调用
     * @param points
     * @return
     */
    @GetMapping(value = "/points/add")
    public Result addPoints(@RequestParam(value = "points") Integer points);
}
