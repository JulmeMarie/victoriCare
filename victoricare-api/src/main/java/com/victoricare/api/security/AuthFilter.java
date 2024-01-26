package com.victoricare.api.security;

import java.io.IOException;
import com.victoricare.api.configurations.GuestConfig;
import com.victoricare.api.entities.LogIn;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMessage;
import com.victoricare.api.exceptions.ActionNotAuthorizedException;
import com.victoricare.api.security.services.UserDetailsImpl;
import com.victoricare.api.security.services.UserDetailsServiceImpl;
import com.victoricare.api.services.ILogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JWTToken jwtToken;

	@Autowired
	private GuestConfig guestConfig;

	@Autowired
	private ILogInService logInService;

	@SuppressWarnings("null")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		this.jwtToken.init();
		boolean isJWTPresent = this.jwtToken.getHttpHeader().getJwt() != null;
		boolean isOffline = SecurityContextHolder.getContext().getAuthentication() == null;

		if (isJWTPresent && isOffline) {
			this.jwtToken.checkApiKey();
			this.doConnect(request);
		}
		/*
		 * response.addHeader("Access-Control-Allow-Origin", "*");
		 * HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		 * httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		 */
		chain.doFilter(request, response);
	}

	public void doConnect(HttpServletRequest request) {
		this.jwtToken.doCheck();
		String username = this.jwtToken.getSubject();
		User user = null;

		if (this.guestConfig.getVisitorName().equals(username)) { // Visitors don't need to login
			user = this.guestConfig.getUser();
		} else {
			LogIn login = this.logInService.doGetByUserAndToken(username, this.jwtToken.getHttpHeader().getJwt());
			if (login == null || login.getDeletionDate() != null) {
				log.error("Invalid login for user : {}", username);
				throw new ActionNotAuthorizedException(EMessage.INVALID_LOGIN);
			}
			user = login.getCreationAuthor();
		}

		UserDetails userDetails = userDetailsService.getUserDetails(user);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetails,
				null,
				userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public static User getOnlineUser(Authentication authentication) {
		if (authentication != null) {
			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
			if (userPrincipal == null) {
				log.error("authenticated user not found in spring security : {}", authentication.getDetails());
				throw new ActionNotAuthorizedException(EMessage.USER_NOT_AUTHENTICATED);
			}
			return userPrincipal.getUser();
		}
		return null;
	}
}