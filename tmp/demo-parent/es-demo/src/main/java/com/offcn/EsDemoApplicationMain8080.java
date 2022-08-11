package com.offcn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.offcn.mapper")
public class EsDemoApplicationMain8080 {

	public static void main(String[] args) {
		SpringApplication.run(EsDemoApplicationMain8080.class, args);
	}

}
