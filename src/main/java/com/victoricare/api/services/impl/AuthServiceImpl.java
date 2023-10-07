package com.victoricare.api.services.impl;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.victoricare.api.dtos.ConnectionDTO;
import com.victoricare.api.dtos.ContentDTO;
import com.victoricare.api.entities.Connection;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.ERight;
import com.victoricare.api.exceptions.ResourceNotFoundException;
import com.victoricare.api.models.ConnectionModel;
import com.victoricare.api.repositories.ConnectionRepository;
import com.victoricare.api.repositories.UserRepository;
import com.victoricare.api.security.jwt.JWTUtils;
import com.victoricare.api.security.services.UserDetailsImpl;
import com.victoricare.api.services.IAuthService;
import com.victoricare.api.services.IFileService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthServiceImpl implements IAuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	private IFileService fileService;

	@Autowired
	private ConnectionRepository connectionRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public ConnectionModel logIn(HttpServletRequest request, AuthenticationManager authenticationManager, ConnectionDTO connectionRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(connectionRequest.getLogin(), connectionRequest.getPassword()));
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		User author = userDetails.getUser();

		// End all past connections with same browser and ip
		this.connectionRepository.endAllConnections(
				userDetails.getUsername(),
				jwtUtils.getIp(request) ,
				jwtUtils.getBrowser(request),
				author);

		Date now = Date.from(Instant.now());

		Connection connection = new Connection();
		connection.setBrowserConnection(jwtUtils.getBrowser(request));
		connection.setCreateAtConnection(now);
		connection.setCreateByConnection(author);
		connection.setIpConnection(jwtUtils.getIp(request));
		connection.setLoginConnection(connectionRequest.getLogin());
		connection.setRightConnection(author.getRightUser());
		connection.setRoleConnection(author.getRoleUser());
		connection.setPasswordConnection(author.getPasswordUser());

		Map<String, Object> jwtResponse = jwtUtils.generateJwtToken(connection);
		connection.setExpireAtConnection((Date)jwtResponse.get("expireAt"));
		connection.setTokenConnection((String)jwtResponse.get("jwt"));
		this.connectionRepository.save(connection);
		
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());

		ConnectionModel model = ConnectionModel.newInstance().init(fileService, connection);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return model;
	}

	@Override
	public ConnectionModel update(User author, ContentDTO contentRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionModel logOut(HttpServletRequest request, User author, Integer userId) {

		if (userId == null) {
			this.connectionRepository.endAllConnections(author.getUsernameUser(), jwtUtils.getIp(request),
					jwtUtils.getBrowser(request), author);
		}

		else if (ERight.isAtLeastAdministrator(author.getRightUser())) {
			Optional<User> user = this.userRepository.findById(userId);
			if(user.isEmpty()) {
				logger.warn("user does not exist : {}",userId);
				throw new ResourceNotFoundException();
			}
			this.connectionRepository.endAllConnections(user.get(), author);
		}
		return this.generateToken(request, null);
	}

	@Override
	public List<ConnectionModel> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionModel generateToken(HttpServletRequest request, User user) {

		Connection connection = new Connection();
		if(user != null) { //user authenticated
			if(user.getUsernameUser() != this.jwtUtils.getVisitorName()) {
				Optional<List<Connection>> connections = this.connectionRepository
					.findByIdentifiers(
						user.getUsernameUser(),
						user.getEmailUser(),
						jwtUtils.getIp(request),
						jwtUtils.getBrowser(request)
					);
				if(connections.isPresent()) {
					connection = connections.get().get(0);
					connection.setUpdateAtConnection(Date.from(Instant.now()));
					connection.setUpdateByConnection(user);
				}
			}
		}

		//If no connection, we simulate a visitor connection
		if(connection.getIdConnection() == null) {
			user = user == null ? jwtUtils.getVisitor() : user;
        	connection.setBrowserConnection(jwtUtils.getBrowser(request));
        	connection.setIpConnection(jwtUtils.getIp(request));
        	connection.setLoginConnection(user.getUsernameUser());
        	connection.setPasswordConnection(user.getPasswordUser());
        	connection.setRightConnection(user.getRightUser());
        	connection.setRoleConnection(user.getRoleUser());
        	connection.setCreateAtConnection(Date.from(Instant.now()));
        	connection.setCreateByConnection(user);
		}

		Map<String, Object> jwtResponse = jwtUtils.generateJwtToken(connection);
		connection.setExpireAtConnection((Date)jwtResponse.get("expireAt"));
		connection.setTokenConnection((String)jwtResponse.get("jwt"));

		//We only update authenticated user connection.
		//Don't save visitor's cause it's simulation (no id attributed)
		if(connection.getIdConnection() != null) {
			this.connectionRepository.save(connection);
		}
		fileService.setS3BucketUrl(this.jwtUtils.getS3BucketUrl());
		return ConnectionModel.newInstance().init(fileService, connection);
	}
}
