package com.sparta.plus.exception;

public class CommentPermissionDeniedException extends ApiException{
	public CommentPermissionDeniedException() {
		super(ErrorCode.COMMENT_PERMISSION_DENIED);
	}
}
