package com.victoricare.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.victoricare.api.enums.ERight;
import com.victoricare.api.security.jwt.AuthEntryPointJWT;
import com.victoricare.api.security.jwt.AuthTokenFilter;
import com.victoricare.api.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebMvc
public class WebSecurityConfig {

	 @Autowired
	  UserDetailsServiceImpl userDetailsService;

	  @Autowired
	  private AuthEntryPointJWT unauthorizedHandler;

	  @Bean
	  public AuthTokenFilter authenticationJwtTokenFilter() {
	    return new AuthTokenFilter();
	  }

	  @Bean
	  public DaoAuthenticationProvider authenticationProvider() {
	      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

	      authProvider.setUserDetailsService(userDetailsService);
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

	    /*http.csrf(csrf -> csrf.disable())*/
		  http.cors().and().csrf().disable()
	        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authorizeHttpRequests(auth ->
	          auth
	              .requestMatchers("/").permitAll()
	              .requestMatchers("/health-check").permitAll()
	              .requestMatchers("/api/token").permitAll()
	              .requestMatchers("/api/test/").permitAll()
	              //.requestMatchers("api/auth/tokens").hasAnyRole(ERight.ADMINISTRATOR.name())
	              //.requestMatchers("/api/auth/login").hasAnyRole(ERight.ANONYMOUS.name())
	              .requestMatchers("/api/right-anm").hasAnyRole(ERight.ANONYMOUS.name(), ERight.USER.name(), ERight.MODERATOR.name(), ERight.ADMINISTRATOR.name())
	              .requestMatchers("/api/right-usr").hasAnyRole(ERight.USER.name(), ERight.MODERATOR.name(), ERight.ADMINISTRATOR.name())
	              .requestMatchers("/api/right-mod").hasAnyRole(ERight.MODERATOR.name(), ERight.ADMINISTRATOR.name())
	              .requestMatchers("/api/right-adm").hasAnyRole(ERight.ADMINISTRATOR.name())
	              .requestMatchers("/").permitAll()
	              .anyRequest().authenticated()
	        );

	    http.authenticationProvider(authenticationProvider());

	    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	  }

}
