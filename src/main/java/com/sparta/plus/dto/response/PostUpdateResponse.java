package com.sparta.plus.dto.response;

import com.sparta.plus.entity.Post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostUpdateResponse {
	private final Long id;
	private final String nickname;
	private final String title;
	private final String content;

	public static PostUpdateResponse from(Post entity) {
		return new PostUpdateResponse(
			entity.getId(),
			entity.getUser().getNickname(),
			entity.getTitle(),
			entity.getContent()
		);
	}
}
