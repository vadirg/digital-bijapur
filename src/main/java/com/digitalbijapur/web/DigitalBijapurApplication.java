package com.digitalbijapur.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.digitalbijapur.controller") 
public class DigitalBijapurApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBijapurApplication.class, args);
	}
}
