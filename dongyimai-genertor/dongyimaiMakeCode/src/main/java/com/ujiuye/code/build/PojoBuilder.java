package com.ujiuye.code.build;

import java.util.Map;

/****
 * @Author:ujiuye
 * @Description:Pojo构建
 * @Date 2021/2/1 14:19
 *****/
public class PojoBuilder {


    /***
     * 构建Pojo
     * @param dataModel
     */
    public static void builder(Map<String,Object> dataModel){
        //生成Pojo层文件
        BuilderFactory.builder(dataModel,
                "/template/pojo",
                "Pojo.java",
                TemplateBuilder.PACKAGE_POJO,
                ".java");
    }

}
