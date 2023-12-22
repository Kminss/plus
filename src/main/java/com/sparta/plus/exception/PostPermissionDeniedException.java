package com.sparta.plus.exception;

public class PostPermissionDeniedException extends ApiException{
	public PostPermissionDeniedException() {
		super(ErrorCode.POST_PERMISSION_DENIED);
	}
}
