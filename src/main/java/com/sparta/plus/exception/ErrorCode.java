package com.sparta.plus.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	INVALID_PASSWORD(400, "유효하지 않은 비밀번호입니다."),
	INVALID_USERNAME(400, "유효하지 않은 아이디입니다.");

	private final int status;
	private final String message;
}
