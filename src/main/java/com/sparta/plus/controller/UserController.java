package com.sparta.plus.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.plus.dto.BaseResponse;
import com.sparta.plus.dto.request.LoginRequest;
import com.sparta.plus.dto.request.SignupRequest;
import com.sparta.plus.dto.response.SignupResponse;
import com.sparta.plus.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	//회원가입
	@PostMapping("/signup")
	public ResponseEntity<BaseResponse<Object>> signup(@Valid @RequestBody SignupRequest request) {
		SignupResponse response = userService.signup(request);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(
				BaseResponse.builder()
					.msg("회원가입 성공")
					.data(response)
					.build()
			);
	}

	//중복된 닉네임
	@GetMapping("/check-nickname")
	public ResponseEntity<BaseResponse<Object>> checkUsername(@RequestParam(name = "nickname") String nickname) {
		userService.checkNickname(nickname);

		return ResponseEntity.ok()
			.body(
				BaseResponse.builder()
					.msg("사용 가능합니다.")
					.build()
			);
	}

	//로그인
	@PostMapping("/login")
	public ResponseEntity<BaseResponse<Object>> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
		userService.login(request, response);
		return ResponseEntity.ok()
			.body(
				BaseResponse.builder()
					.msg("로그인 성공")
					.build()
			);
	}

}

