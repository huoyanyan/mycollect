package com.boot.test.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class Application {

	public static void main(String[] args) {
		// http://localhost:8080/
		SpringApplication.run(Application.class, args);
	}
}