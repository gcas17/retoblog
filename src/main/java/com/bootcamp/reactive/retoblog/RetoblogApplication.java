package com.bootcamp.reactive.retoblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableWebFlux
public class RetoblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetoblogApplication.class, args);
	}

}
