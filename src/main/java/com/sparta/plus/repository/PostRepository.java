package com.sparta.plus.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.plus.entity.Post;
import com.sparta.plus.exception.ApiException;
import com.sparta.plus.exception.ErrorCode;
import com.sparta.plus.repository.querydsl.PostRepositoryCustom;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

	default Post getPost(Long postId) {
		return findById(postId).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_POST));
	}

	List<Post> findAllByModifiedDateTimeBefore(LocalDateTime dateTime);
}
