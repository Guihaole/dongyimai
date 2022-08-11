package com.ujiuye.code.build;

import java.util.Map;

/*****
 * @Author: ujiuye
 * @Date: 2021/2/1 14:19
 * @Description:
 *  生成Swagger
 ****/
public class SwaggerBuilder {

    /***
     * ServiceImpl构建
     * @param modelMap
     */
    public static void builder(Map<String,Object> modelMap){
        //swagger的文件名字
        modelMap.put("Table","swagger");

        //生成ServiceImpl层文件
        BuilderFactory.builder(modelMap,
                "/template/swagger",
                "swagger.json",
                TemplateBuilder.SWAGGERUI_PATH,
                ".json");
    }
}
