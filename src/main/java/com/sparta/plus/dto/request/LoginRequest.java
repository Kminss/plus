package com.sparta.plus.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequest {
	@NotBlank
	private final String nickname;
	@NotBlank
	private final String password;

}
