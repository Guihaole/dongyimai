package com.edu.commons;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lcj
 * @Date: 2020/10/27 10:31
 * @Description: 统一返回结果类
 * @Version: 0.0.1
 */
@Data
public class ResultData {

    /**是否成功*/
    private Boolean success;
    /**返回编码*/
    private Integer code;
    /**返回消息*/
    private String message;
    /**返回数据*/
    private Map<String, Object> data = new HashMap<String, Object>();

    public ResultData() {
    }

    /**
     * 成功静态方法
     * @return
     */
    public static ResultData ok() {
        ResultData result = new ResultData();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("成功!");
        return result;
    }

    /**
     * 成功静态方法
     * @return
     */
    public static ResultData ok(Integer code, String message) {
        ResultData result = new ResultData();
        result.setSuccess(true);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败静态方法
     * @return
     */
    public static ResultData error() {
        ResultData result = new ResultData();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR);
        result.setMessage("失败!");
        return result;
    }

    /**
     * 失败静态方法
     * @return
     */
    public static ResultData error(Integer code, String message) {
        ResultData result = new ResultData();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public ResultData success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public ResultData message(String message){
        this.setMessage(message);
        return this;
    }

    public ResultData code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResultData data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public ResultData data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
