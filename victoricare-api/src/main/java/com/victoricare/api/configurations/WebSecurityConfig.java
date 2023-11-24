package com.victoricare.api.configurations;

import lombok.extern.slf4j.Slf4j;
import com.victoricare.api.security.AuthFilter;
import org.springframework.context.annotation.Bean;
import com.victoricare.api.security.AuthEntryPointJWT;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

	@Autowired
	private AuthEntryPointJWT unauthorizedHandler;

	@Autowired
	private AuthFilter authFilter;

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(this.authFilter.getUserDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/test").permitAll()
						.requestMatchers("/actuator/**").permitAll()
						.requestMatchers("/api-docs/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
						.requestMatchers("/custom/actuator/**").permitAll()
						.requestMatchers("/auths/token").permitAll()
						.requestMatchers("/signups/**").permitAll()
						.anyRequest().authenticated());
		http.authenticationProvider(authProvider());
		http.addFilterBefore(this.authFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}