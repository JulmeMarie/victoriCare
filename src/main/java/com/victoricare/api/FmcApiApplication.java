package com.victoricare.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@SpringBootApplication
@EnableTransactionManagement
public class FmcApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FmcApiApplication.class, args);
	}

	/*
	 * @Bean
	 * public WebMvcConfigurer corsConfigurer() {
	 * return new WebMvcConfigurer() {
	 * 
	 * @Override
	 * public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/api").allowedOrigins("http://localhost:3000");
	 * }
	 * };
	 * }
	 */

	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> index(HttpServletRequest request, Authentication authentication) {
		return ResponseEntity.ok().build();
	}

	@GetMapping(path = "/health-check", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> healthCheck(HttpServletRequest request, Authentication authentication) {
		return ResponseEntity.ok().build();
	}

}
