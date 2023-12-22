package com.sparta.plus.dto.response;

import com.sparta.plus.entity.Comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentCreateResponse {
	private final Long id;

	public static CommentCreateResponse from(Comment entity) {
		return new CommentCreateResponse(entity.getId());
	}
}
