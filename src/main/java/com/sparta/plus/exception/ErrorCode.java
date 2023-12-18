package com.sparta.plus.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	INVALID_PASSWORD(400, "유효하지 않은 비밀번호입니다."),
	INVALID_NICKNAME(400, "유효하지 않은 닉네임입니다."),

	ALREADY_EXIST_NICKNAME(409, "이미 닉네임이 존재합니다." );

	private final int status;
	private final String message;
}
