package com.maple.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunApplication {

	public static void main(String[] args) {
		SpringApplication sa=new SpringApplication(RunApplication.class);
		// 禁用devTools热部署
		//System.setProperty("spring.devtools.restart.enabled", "false");
		// 禁用命令行更改application.properties属性
		sa.setAddCommandLineProperties(false);
		sa.run(args);
	}
}
