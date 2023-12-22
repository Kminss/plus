package com.sparta.plus.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.plus.dto.request.CommentRequest;
import com.sparta.plus.dto.response.CommentCreateResponse;
import com.sparta.plus.dto.response.CommentUpdateResponse;
import com.sparta.plus.entity.Comment;
import com.sparta.plus.entity.Post;
import com.sparta.plus.entity.User;
import com.sparta.plus.exception.CommentPermissionDeniedException;
import com.sparta.plus.repository.CommentRepository;
import com.sparta.plus.repository.PostRepository;
import com.sparta.plus.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	@Override
	@Transactional
	public CommentCreateResponse create(Long postId, CommentRequest request, User user) {
		Post findPost = postRepository.getPost(postId);
		Comment comment = request.toEntity(findPost, user);

		return CommentCreateResponse.from(commentRepository.save(comment));
	}

	@Override
	@Transactional
	public CommentUpdateResponse update(Long postId, Long commentId, CommentRequest request, User user) {
		Comment comment = commentRepository.getComment(commentId);

		checkPermission(comment.getUser(), user);
		comment.update(request);

		return CommentUpdateResponse.from(comment);
	}

	@Override
	@Transactional
	public void delete(Long postId, Long commentId, User user) {
		Comment comment = commentRepository.getComment(commentId);

		checkPermission(comment.getUser(), user);

		commentRepository.delete(comment);
	}

	private static void checkPermission(User commentUser, User loginUser) {
		if (!commentUser.equals(loginUser)) {
			throw new CommentPermissionDeniedException();
		}
	}
}
