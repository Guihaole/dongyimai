package com.edu.controller;



import com.edu.commons.ResultCode;
import com.edu.commons.ResultData;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author offcn
 * @since 2021-11-16
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user:list')")
    public ResultData userList() {
        return ResultData.ok(ResultCode.SUCCESS, "访问用户查询界面成功!");
    }
    @GetMapping("/add")
    @PreAuthorize("hasAuthority('user:add')")
    public ResultData userAdd() {
        return ResultData.ok(ResultCode.SUCCESS, "访问用户新增界面成功!");
    }
    /**
     * 测试无权限访问，数据库中权限是user:update
     * @return
     */
    @GetMapping("/update")
    @PreAuthorize("hasAuthority('user:update')")
    public ResultData userUpdate() {
        return ResultData.ok(ResultCode.SUCCESS, "访问用户修改界面成功!");
    }

    @GetMapping("/delete")
    @Secured("ROLE_admin")
    public ResultData userDelete() {
        return ResultData.ok(ResultCode.SUCCESS, "访问用户删除界面成功!");
    }

}

