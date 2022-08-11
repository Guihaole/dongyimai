package com.ujiuye.code.build;

import java.util.Map;

/****
 * @Author:ujiuye
 * @Description:Service构建
 * @Date 2021/2/1 14:19
 *****/
public class ServiceBuilder {


    /***
     * 构建Service
     * @param modelMap
     */
    public static void builder(Map<String,Object> modelMap){
        //生成Service层文件
        BuilderFactory.builder(modelMap,
                "/template/service",
                "Service.java",
                TemplateBuilder.PACKAGE_SERVICE_INTERFACE,
                "Service.java");
    }

}
