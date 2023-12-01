package com.victoricare.api.security.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.victoricare.api.entities.Connection;
import com.victoricare.api.entities.User;
import com.victoricare.api.repositories.ConnectionRepository;
import com.victoricare.api.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ConnectionRepository connectionRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsernameUser(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }

  public UserDetails getUserDetails(User user) {
	return UserDetailsImpl.build(user);
  }

  public Connection getConnectionByToken(String token) {
	  List<Connection> connections = this.connectionRepository.findByTokenConnection(token);
	  return connections != null && connections.size() >= 1 ? connections.get(0) : null;
  }
}