package com.sparta.plus.service.impl;

import static com.sparta.plus.exception.ErrorCode.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.plus.dto.TokenDto;
import com.sparta.plus.dto.request.LoginRequest;
import com.sparta.plus.dto.request.SignupRequest;
import com.sparta.plus.dto.response.SignupResponse;
import com.sparta.plus.entity.User;
import com.sparta.plus.entity.UserRoleEnum;
import com.sparta.plus.exception.ApiException;
import com.sparta.plus.exception.MisMatchedLoginException;
import com.sparta.plus.repository.UserRepository;
import com.sparta.plus.security.CustomUserDetails;
import com.sparta.plus.service.UserService;
import com.sparta.plus.util.JwtUtils;
import com.sparta.plus.util.RedisUtils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	private final RedisUtils redisUtils;

	@Override
	@Transactional
	public SignupResponse signup(SignupRequest request) {

		validPassword(request);
		checkNickname(request.getNickname());

		User user = User.of(request.getNickname(), passwordEncoder.encode(request.getPassword()), UserRoleEnum.USER);
		return SignupResponse.from(userRepository.save(user));
	}

	@Override
	public void checkNickname(String nickname) {
		if (userRepository.existsByNickname(nickname)) {
			throw new ApiException(ALREADY_EXIST_NICKNAME);
		}

	}

	@Override
	@Transactional
	public void login(LoginRequest request, HttpServletResponse response) {
		User user = findUser(request);

		if (!checkLoginUser(request, user)) {
			throw new MisMatchedLoginException();
		}

		//인증객체 생성
		Authentication authentication = createAuthentication(user);

		//SecurityContext 에 인증객체 세팅
		setAuthentication(authentication);

		//토큰 생성
		TokenDto tokenDto = jwtUtils.generateToken(user.getNickname(), user.getRole());

		//response에 저장
		jwtUtils.setTokenForCookie(tokenDto, response);

		//Redis에 RefreshToken 저장
		String key = "refreshToken: " + user.getNickname();
		redisUtils.saveKey(key, Math.toIntExact(jwtUtils.getRefreshTokenExpiration() / 1000),tokenDto.getRefreshToken());
	}

	private Authentication createAuthentication(User user) {
		CustomUserDetails userDetails = new CustomUserDetails(user);
		return new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), userDetails.getAuthorities());
	}

	private void setAuthentication(Authentication authentication) {
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(authentication);
	}

	private boolean checkLoginUser(LoginRequest request, User user) {
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			return false;
		}

		return user.getNickname().equals(request.getNickname());
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

	public User findUser(LoginRequest request) {
		return userRepository.findByNickname(request.getNickname())
			.orElseThrow(() -> new ApiException(NOT_FOUND_USER));
	}
}
