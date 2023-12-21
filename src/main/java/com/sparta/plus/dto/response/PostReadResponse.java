package com.sparta.plus.dto.response;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class PostReadResponse {
	private final Long id;
	private final String nickname;
	private final String title;
	private final LocalDateTime createdDateTime;

	@QueryProjection
	public PostReadResponse(Long id, String nickname, String title, LocalDateTime createdDateTime) {
		this.id = id;
		this.nickname = nickname;
		this.title = title;
		this.createdDateTime = createdDateTime;
	}
}
