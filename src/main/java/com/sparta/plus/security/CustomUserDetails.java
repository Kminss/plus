package com.sparta.plus.security;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sparta.plus.entity.User;
import com.sparta.plus.entity.UserRoleEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails, Serializable {
	@Serial
	private static final long serialVersionUID = 1977936443460921608L;

	private final User user;

	@Override
	public String getUsername() {
		return user.getNickname();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		UserRoleEnum role = user.getRole();

		return List.of(role::getAuthority);
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
}
