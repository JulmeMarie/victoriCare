package com.victoricare.api.security.jwt;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.victoricare.api.entities.User;
import com.victoricare.api.exceptions.AuthException;
import com.victoricare.api.security.services.UserDetailsImpl;
import com.victoricare.api.security.services.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
       
		if(!List.of("/", "/api/health-check").contains(request.getRequestURI().trim())) {
			System.out.println("endpoint :" + request.getRequestURI().trim());
			//this.jwtUtils.checkApiKey(request);
		}

		// Once we get the token validate it.
		if (this.jwtUtils.parseJwt(request) != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			this.jwtUtils.validateJwtToken(request);

			User user = this.jwtUtils.checkConnection(userDetailsService, request);
			UserDetails userDetails = userDetailsService.getUserDetails(user);

			UsernamePasswordAuthenticationToken authentication =
		            new UsernamePasswordAuthenticationToken(
		                userDetails,
		                null,
		                userDetails.getAuthorities());
		    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		    SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		/*
		response.addHeader("Access-Control-Allow-Origin", "*");
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");*/
		chain.doFilter(request, response);
	}

	public static User getOnlineUser(Authentication authentication) {
		if(authentication == null) return null;

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		if(userPrincipal == null) {
			logger.error("authenticated user not found in spring security : {}", authentication.getDetails());
			throw new AuthException();
		}
		return userPrincipal.getUser();
	}
}