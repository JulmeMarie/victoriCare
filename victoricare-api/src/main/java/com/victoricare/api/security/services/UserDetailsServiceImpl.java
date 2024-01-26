package com.victoricare.api.security.services;

import com.victoricare.api.repositories.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.victoricare.api.entities.User;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {

		List<User> exisitingUsers = this.userRepository.findByEmailOrPseudo(identifier, identifier);

		// No User or multiple users found
		if (exisitingUsers.isEmpty() || exisitingUsers.size() > 1) {
			throw new UsernameNotFoundException("User Not Found with identifier: " + identifier);
		}
		return UserDetailsImpl.build(exisitingUsers.get(0));
	}

	public UserDetails getUserDetails(User user) {
		return UserDetailsImpl.build(user);
	}
}