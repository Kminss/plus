package com.sparta.plus.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.plus.config.PasswordEncoderConfig;
import com.sparta.plus.dto.request.SignupRequest;
import com.sparta.plus.dto.response.SignupResponse;
import com.sparta.plus.entity.User;
import com.sparta.plus.entity.UserRoleEnum;
import com.sparta.plus.exception.ApiException;
import com.sparta.plus.repository.UserRepository;
import com.sparta.plus.service.impl.UserServiceImpl;

@Import(PasswordEncoderConfig.class)
@DisplayName("서비스 테스트 - 유저")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@InjectMocks
	private UserServiceImpl sut;

	@Mock
	UserRepository userRepository;
	@Mock
	PasswordEncoder passwordEncoder;

	@Test
	void 회원가입_테스트 () {
	    //Given
		SignupRequest request = SignupRequest.of("nickname", "password", "password");
		User user = User.of(request.getNickname(), passwordEncoder.encode(request.getPassword()), UserRoleEnum.USER);
		ReflectionTestUtils.setField(user, "id", 1L);
		given(userRepository.save(any())).willReturn(user);

	    //When
		SignupResponse response = sut.signup(request);

	    //Then
		assertThat(response).isNotNull();
		assertThat(response.getId()).isNotNull();
	}

	@Test
	void 회원가입_실패_테스트 ()  {
		//Given
		SignupRequest request = SignupRequest.of("nickname", "password", "password1");
		User user = User.of(request.getNickname(), passwordEncoder.encode(request.getPassword()), UserRoleEnum.USER);
		ReflectionTestUtils.setField(user, "id", 1L);

		//When && Then
		assertThatThrownBy(() -> sut.signup(request)).isInstanceOf(ApiException.class);
	}
}