package com.sparta.plus.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
	private final ErrorCode errorCode;

	public ApiException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
