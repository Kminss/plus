package com.sparta.plus.exception;

public class MisMatchedLoginException extends ApiException {
	public MisMatchedLoginException() {
		super(ErrorCode.MISMATCHED_LOGIN_INPUT);
	}
}
