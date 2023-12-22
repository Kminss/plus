package com.sparta.plus.dto.request;

import com.sparta.plus.entity.Comment;
import com.sparta.plus.entity.Post;
import com.sparta.plus.entity.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentRequest {
	private final String content;

	public Comment toEntity(Post post, User user) {
		return Comment.of(
			content,
			post,
			user
		);
	}
}
