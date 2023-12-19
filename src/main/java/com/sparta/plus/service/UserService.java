package com.sparta.plus.service;

import com.sparta.plus.dto.request.LoginRequest;
import com.sparta.plus.dto.request.SignupRequest;
import com.sparta.plus.dto.response.SignupResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
	/**
	 * 회원가입에 필요한 정보를 받아서 회원가입을 하는 메소드입니다.
	 * @param request - 회원가입 요청 DTO
	 * @return 회원가입된 유저의 식별자 ID
	 */
	SignupResponse signup(SignupRequest request);

	/**
	 * 로그인 닉네임 중복여부 확인 메소드입니다.
	 * @param nickname - 로그인 닉네임
	 * @exception com.sparta.plus.exception.ApiException - 이미 닉네임이 존재하는 경우
	 */
	void checkNickname(String nickname);

	/**
	 *
	 * @param request - 로그인 요청 dto
	 * @param response - HttpServletResponse
	 */
	void login(LoginRequest request, HttpServletResponse response);
}
