package com.sparta.plus.service.impl;

import static com.sparta.plus.exception.ErrorCode.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.plus.dto.request.SignupRequest;
import com.sparta.plus.dto.response.SignupResponse;
import com.sparta.plus.entity.User;
import com.sparta.plus.exception.ApiException;
import com.sparta.plus.repository.UserRepository;
import com.sparta.plus.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public SignupResponse signup(SignupRequest request) {
		validPassword(request);
		User user = User.of(request.getNickname(), passwordEncoder.encode(request.getPassword()));
		return SignupResponse.from(userRepository.save(user));
	}

	@Override
	public void checkNickname(String nickname) {
		if (userRepository.existsByNickname(nickname)) {

			throw new ApiException(ALREADY_EXIST_NICKNAME);
		}

	}

	private void validPassword(SignupRequest request) {
		String nickname = request.getNickname();
		String password = request.getPassword();
		String confirmPassword = request.getConfirmPassword();

		//비밀번호에 아이디값이 있는지 확인
		if (password.contains(nickname)) {
			throw new ApiException(INVALID_NICKNAME);
		}

		//비밀번호, 비밀번호 확인 일치하는지 검증
		if (!password.equals(confirmPassword)) {
			throw new ApiException(INVALID_PASSWORD);
		}
	}
}
