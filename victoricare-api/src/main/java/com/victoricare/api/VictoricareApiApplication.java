package com.victoricare.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class VictoricareApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VictoricareApiApplication.class, args);
	}

}
