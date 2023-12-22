package com.sparta.plus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.plus.dto.BaseResponse;
import com.sparta.plus.dto.request.CommentRequest;
import com.sparta.plus.dto.response.CommentCreateResponse;
import com.sparta.plus.dto.response.CommentUpdateResponse;
import com.sparta.plus.security.CustomUserDetails;
import com.sparta.plus.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/comments")
@RestController
public class CommentController {

	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<BaseResponse<Object>> create(
		@PathVariable Long postId,
		@Valid @RequestBody CommentRequest request,
		Authentication authentication
	) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		CommentCreateResponse response = commentService.create(postId, request, userDetails.getUser());

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(
				BaseResponse.builder()
					.msg("댓글 생성 성공")
					.data(response)
					.build()
			);
	}

	@PutMapping("/{commentId}")
	public ResponseEntity<BaseResponse<Object>> update(
		@PathVariable Long postId,
		@PathVariable Long commentId,
		@Valid @RequestBody CommentRequest request,
		Authentication authentication
	) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		CommentUpdateResponse response = commentService.update(postId, commentId, request, userDetails.getUser());

		return ResponseEntity.status(HttpStatus.OK)
			.body(
				BaseResponse.builder()
					.msg("댓글 수정 성공")
					.data(response)
					.build()
			);
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<BaseResponse<Object>> delete(
		@PathVariable Long postId,
		@PathVariable Long commentId,
		Authentication authentication
	) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		commentService.delete(postId, commentId, userDetails.getUser());

		return ResponseEntity.status(HttpStatus.OK)
			.body(
				BaseResponse.builder()
					.msg("댓글 삭제 성공")
					.build()
			);
	}
}
