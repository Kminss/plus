package com.sparta.plus.dto.response;

import java.time.LocalDateTime;

import com.sparta.plus.entity.Comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentUpdateResponse {
	private final Long id;
	private final String nickname;
	private final String content;
	private final LocalDateTime createdDateTime;

	public static CommentUpdateResponse from(Comment entity) {
		return new CommentUpdateResponse(
			entity.getId(),
			entity.getUser().getNickname(),
			entity.getContent(),
			entity.getCreatedDateTime()
		);
	}
}
