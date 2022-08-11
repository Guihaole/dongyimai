package com.edu.commons;

/**
 * @ClassName ResultCode
 * @Description 统一返回状态码
 * @Author liucaijing
 * @Date 2020/10/10:00
 * @Version 1.0
 */
public interface ResultCode {

    /**成功*/
    public static Integer SUCCESS = 20000;
    /**失败*/
    public static Integer ERROR = 20001;
    /**未授权(匿名)*/
    public static Integer UNAUTHORIZED_01 = 20002;
    /**未授权(认证后)*/
    public static Integer UNAUTHORIZED_02 = 20003;
}
