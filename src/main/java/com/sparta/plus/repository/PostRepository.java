package com.sparta.plus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.plus.entity.Post;
import com.sparta.plus.exception.ApiException;
import com.sparta.plus.exception.ErrorCode;

public interface PostRepository extends JpaRepository<Post, Long> {
	default Post getPost(Long postId) {
		return findById(postId).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_POST));
	}
}
