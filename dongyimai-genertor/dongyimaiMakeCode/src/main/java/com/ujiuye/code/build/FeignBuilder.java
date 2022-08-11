package com.ujiuye.code.build;

import java.util.Map;

/****
 * @Author:ujiuye
 * @Description:Feign构建
 * @Date 2021/2/1 14:19
 *****/
public class FeignBuilder {


    /***
     * 构建Feign
     * @param modelMap
     */
    public static void builder(Map<String,Object> modelMap){
        //生成Dao层文件
        BuilderFactory.builder(modelMap,
                "/template/feign",
                "Feign.java",
                TemplateBuilder.PACKAGE_FEIGN,
                "Feign.java");
    }

}
