package com.sparta.plus.dto.response;

import com.sparta.plus.entity.Post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostCreateResponse {
	private final Long id;

	public static PostCreateResponse from(Post entity) {
		return new PostCreateResponse(entity.getId());
	}
}
