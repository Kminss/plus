package com.sparta.plus.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.plus.dto.request.PostRequest;
import com.sparta.plus.dto.response.PostCreateResponse;
import com.sparta.plus.dto.response.PostGetResponse;
import com.sparta.plus.entity.Post;
import com.sparta.plus.entity.User;
import com.sparta.plus.service.PostService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	@Override
	@Transactional
	public PostCreateResponse create(PostRequest request, User user) {
		Post post = request.toEntity(user);

		return PostCreateResponse.from(postRepository.save(post));
	}

	@Override
	@Transactional(readOnly = true)
	public PostGetResponse get(Long postId) {
		return PostGetResponse.from(postRepository.findPost(postId));
	}
}
