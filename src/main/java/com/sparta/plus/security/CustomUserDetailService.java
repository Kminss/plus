package com.sparta.plus.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sparta.plus.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	private final UserRepository userRepository;

	public CustomUserDetailService(UserRepository memberRepository) {
		this.userRepository = memberRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByNickname(username)
			.map(CustomUserDetails::new)
			.orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다. " + username));
	}
}