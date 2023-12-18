package com.sparta.plus.dto.response;

import com.sparta.plus.entity.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class SignupResponse {
	private final Long id;

	public static SignupResponse from(User entity) {
		return new SignupResponse(entity.getId());
	}
}
