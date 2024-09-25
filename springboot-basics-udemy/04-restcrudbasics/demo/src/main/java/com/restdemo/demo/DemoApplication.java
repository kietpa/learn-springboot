package com.restdemo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		System.out.println("Starting server on :8080...");
		SpringApplication.run(DemoApplication.class, args);
	}

}
