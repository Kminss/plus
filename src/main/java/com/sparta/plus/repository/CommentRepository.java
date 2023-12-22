package com.sparta.plus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.plus.entity.Comment;
import com.sparta.plus.exception.ApiException;
import com.sparta.plus.exception.ErrorCode;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	default Comment getComment(Long commentId) {
		return findById(commentId).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_COMMENT));
	}
}
