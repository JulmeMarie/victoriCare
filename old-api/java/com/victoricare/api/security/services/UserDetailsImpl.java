package com.victoricare.api.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.ERight;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 8083881909261020138L;

	private Integer id;

	private String username;

	private String email;

	private String role;

	private String right;

	@JsonIgnore
	private String password;

	private User user;


	private Collection<? extends GrantedAuthority> authorities;


	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = ERight.compute(ERight.get(user.getRightUser())).stream()
				.map(role -> new SimpleGrantedAuthority(role.name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getIdUser(),
				user.getUsernameUser(),
				user.getEmailUser(),
				user.getRoleUser(),
				user.getRightUser(),
				user.getPasswordUser(),
				user,
				authorities);
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
