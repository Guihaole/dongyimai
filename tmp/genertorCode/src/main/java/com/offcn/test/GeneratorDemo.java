package com.offcn.test;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class GeneratorDemo {
    public static void main(String[] args) {
        // 代码生成器对象：
        AutoGenerator mpg = new AutoGenerator();

        // 1：全局配置
        GlobalConfig gc = new GlobalConfig();//注意导入包的时候， 是generator包当中的对象
        //获取项目路径
        String projectPath = "E:/mavenoffcnidea-work-goon/tmp";
        System.out.println("projectPath:"+projectPath);
        //mybatisplusDemo04:为项目中模块的名称
        gc.setOutputDir(projectPath + "/genertorCode/src/main/java");
        gc.setAuthor("qf_meng");//设置作者
        gc.setOpen(false);//生成时候是否打开资源管理器
        gc.setFileOverride(false);//重新生成文件时是否覆盖
        gc.setServiceName("%sService"); //生成service时候去掉I IService
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);


        //2: DataSource
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/goods?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("1234");
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert(){
            //⾃定义数据库表字段类型转换【可选】
            @Override
            public IColumnType processTypeConvert(GlobalConfig gc, String fieldType){
                System.out.println("转换类型："+ fieldType);
                //默认会把⽇期类型转为LocalDateTime，在查询的时候会报错，这⾥改为Date
                String t = fieldType.toLowerCase();
                if(t.contains("date")|| t.contains("time")|| t.contains("year")){
                    return DbColumnType.DATE;
                }else{
                    return super.processTypeConvert(gc, fieldType);
                }
            }
        });
        mpg.setDataSource(dsc);



        // 3:包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(null);
        pc.setParent("com.offcn");
        pc.setController("controller");
        pc.setEntity("pojo");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        //4:
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setEntityLombokModel(true);
        //要生成的表的名称
        String  tableNames="goods";
        strategy.setInclude(tableNames.split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("t_");//设置过滤表前缀
        mpg.setStrategy(strategy);
        mpg.execute();

        //5:执行
        mpg.execute();
    }
}
