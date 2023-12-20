package com.sparta.plus.dto.request;

import com.sparta.plus.entity.Post;
import com.sparta.plus.entity.User;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostRequest {

	@Size(max = 500, message = "500자 이하로 입력 가능")
	private final String title;

	@Size(max = 5000, message = "5000자 이하로 입력 가능")
	private final String content;

	public Post toEntity(User user) {
		return Post.of(title, content, user);
	}
}
