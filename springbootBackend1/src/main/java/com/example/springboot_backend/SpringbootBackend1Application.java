package com.example.springboot_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringbootBackend1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackend1Application.class, args);
	}

}
