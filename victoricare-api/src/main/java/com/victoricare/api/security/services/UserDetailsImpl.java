package com.victoricare.api.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.victoricare.api.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 8083881909261020138L;

	private Integer id;

	private String username;

	private String email;

	private String[] roles;

	private String[] rights;

	@JsonIgnore
	private String password;

	private User user;

	private Collection<? extends GrantedAuthority> authorities;

	public static UserDetailsImpl build(User user) {
		String[] userRights = user.getRights().split(",");
		String[] userRoles = user.getRoles().split(",");
		List<SimpleGrantedAuthority> userAuthorities = Stream.of(userRights).map(SimpleGrantedAuthority::new).toList();

		return new UserDetailsImpl(
				user.getId(),
				user.getPseudo(),
				user.getEmail(),
				userRoles,
				userRights,
				user.getPass(),
				user,
				userAuthorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
