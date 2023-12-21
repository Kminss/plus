package com.sparta.plus.exception;

public class PostPermissionDeniedException extends ApiException{
	public PostPermissionDeniedException() {
		super(ErrorCode.INVALID_PASSWORD);
	}
}
