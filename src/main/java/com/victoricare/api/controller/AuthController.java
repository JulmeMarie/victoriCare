package com.victoricare.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.victoricare.api.dtos.ConnectionDTO;
import com.victoricare.api.models.ConnectionModel;
import com.victoricare.api.security.jwt.AuthTokenFilter;
import com.victoricare.api.services.IAuthService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthController {

	@Autowired
	public IAuthService authService;

	@Autowired
	public AuthenticationManager authenticationManager;

    @Autowired
	PasswordEncoder encoder;
    
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
   	public ResponseEntity<?>  index(HttpServletRequest request, Authentication authentication) {
   		return ResponseEntity.ok().build();
   	}
    
    @GetMapping(path = "/health-check", produces = MediaType.APPLICATION_JSON_VALUE)
   	public ResponseEntity<?>  healthCheck(HttpServletRequest request, Authentication authentication) {
   		return ResponseEntity.ok().build();
   	}

	@PutMapping(path = "api/right-anm/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ConnectionModel> logIn(HttpServletRequest request, @RequestBody ConnectionDTO connectionReq) {
		return ResponseEntity.ok(authService.logIn(request, authenticationManager, connectionReq));
	}

	@RequestMapping(value = {"api/right-adm/logout/{id}", "api/right-usr/logout"}, method = RequestMethod.DELETE)
	public ResponseEntity<ConnectionModel> logOut(HttpServletRequest request, Authentication authentication, @PathVariable(required = false) Integer userId) {
		return ResponseEntity.ok(authService.logOut( request, AuthTokenFilter.getOnlineUser(authentication), userId));
	}

	@GetMapping(path = "api/token", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ConnectionModel> generateToken(HttpServletRequest request, Authentication authentication) {
		return ResponseEntity.ok(authService.generateToken(request, AuthTokenFilter.getOnlineUser(authentication)));
	}

	@GetMapping(path = "api/right-adm/connections", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ConnectionModel>> list() {
		return ResponseEntity.ok(authService.list());
	}
}
