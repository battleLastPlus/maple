package com.maple.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunApplication {

	public static void main(String[] args) {
		SpringApplication sa=new SpringApplication(RunApplication.class);
		// ����devTools�Ȳ���
		//System.setProperty("spring.devtools.restart.enabled", "false");
		// ���������и���application.properties����
		sa.setAddCommandLineProperties(false);
		sa.run(args);
	}
}
