package com.sparta.plus.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	INVALID_PASSWORD(400, "유효하지 않은 비밀번호입니다."),
	INVALID_NICKNAME(400, "유효하지 않은 닉네임입니다."),
	MISMATCHED_LOGIN_INPUT(400, "닉네임 또는 패스워드를 확인해주세요."),

	UNAUTHORIZED(401, "JWT 인증 실패"),

	POST_PERMISSION_DENIED(403, "해당 게시글에 대한 권한이 없습니다."),
	COMMENT_PERMISSION_DENIED(403, "해당 댓글에 대한 권한이 없습니다."),

	NOT_FOUND_USER(404, "유저를 찾을 수 없습니다."),
	NOT_FOUND_POST(404, "게시글을 찾을 수 없습니다."),
	NOT_FOUND_COMMENT(404, "댓글을 찾을 수 없습니다."),

	ALREADY_EXIST_NICKNAME(409, "이미 닉네임이 존재합니다." );

	private final int status;
	private final String message;
}
