package com.ujiuye.code.build;

import java.util.Map;

/****
 * @Author:ujiuye
 * @Description:Dao构建
 * @Date 2021/2/1 14:19
 *****/
public class DaoBuilder {


    /***
     * 构建Dao
     * @param modelMap
     */
    public static void builder(Map<String,Object> modelMap){
        //生成Dao层文件
        BuilderFactory.builder(modelMap,
                "/template/dao",
                "Mapper.java",
                TemplateBuilder.PACKAGE_MAPPER,
                "Mapper.java");
    }

}
