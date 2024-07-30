package com.study.iqtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.study.iqtest"})
public class IqtestApplication {

	public static void main(String[] args) {
		SpringApplication.run(IqtestApplication.class, args);
	}

}
