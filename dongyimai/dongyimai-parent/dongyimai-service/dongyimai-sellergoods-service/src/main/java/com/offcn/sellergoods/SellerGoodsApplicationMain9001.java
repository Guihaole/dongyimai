package com.offcn.sellergoods;

import com.offcn.interceptor.FeignInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@MapperScan("com.offcn.sellergoods.dao")
public class SellerGoodsApplicationMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(SellerGoodsApplicationMain9001.class,args);
    }
    /***
     * 创建拦截器Bean对象
     * @return fegin请求传递令牌
     */
    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }
}
