package com.sparta.plus.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.plus.dto.request.PostRequest;
import com.sparta.plus.dto.response.PostCreateResponse;
import com.sparta.plus.dto.response.PostGetResponse;
import com.sparta.plus.dto.response.PostReadResponse;
import com.sparta.plus.dto.response.PostUpdateResponse;
import com.sparta.plus.entity.Post;
import com.sparta.plus.entity.User;
import com.sparta.plus.exception.PostPermissionDeniedException;
import com.sparta.plus.repository.PostRepository;
import com.sparta.plus.service.PostService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	@Override
	@Transactional
	public PostCreateResponse create(PostRequest request, User loginUser) {
		Post post = request.toEntity(loginUser);

		return PostCreateResponse.from(postRepository.save(post));
	}

	@Override
	@Transactional(readOnly = true)
	public PostGetResponse get(Long postId) {
		return PostGetResponse.from(postRepository.getPost(postId));
	}

	@Override
	@Transactional
	public PostUpdateResponse update(Long postId, PostRequest request, User loginUser) {
		Post findPost = postRepository.getPost(postId);
		User postUser = findPost.getUser();

		checkPermission(postUser, loginUser);
		findPost.update(request);

		return PostUpdateResponse.from(findPost);
	}

	@Override
	@Transactional
	public void delete(Long postId, User loginUser) {
		Post findPost = postRepository.getPost(postId);
		User postUser = findPost.getUser();

		checkPermission(postUser, loginUser);
		postRepository.delete(findPost);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PostReadResponse> read(Pageable pageable) {
		return postRepository.getPosts(pageable);
	}

	private static void checkPermission(User postUser, User loginUser) {
		if (!postUser.equals(loginUser)) {
			throw new PostPermissionDeniedException();
		}
	}
}
