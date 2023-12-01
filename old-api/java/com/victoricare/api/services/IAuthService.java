package com.victoricare.api.services;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;

import com.victoricare.api.dtos.ConnectionDTO;
import com.victoricare.api.dtos.ContentDTO;
import com.victoricare.api.entities.User;
import com.victoricare.api.models.ConnectionModel;

import jakarta.servlet.http.HttpServletRequest;

public interface IAuthService {
	public ConnectionModel logIn(HttpServletRequest request, AuthenticationManager authenticationManager, ConnectionDTO connectionRequest);

	public ConnectionModel logOut(HttpServletRequest request, User author, Integer userId);

	public ConnectionModel update(User author, ContentDTO contentRequest);


	public List<ConnectionModel> list();

	public ConnectionModel generateToken(HttpServletRequest request, User user);
}
