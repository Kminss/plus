package com.sparta.plus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.plus.dto.BaseResponse;
import com.sparta.plus.dto.request.PostRequest;
import com.sparta.plus.dto.response.PostCreateResponse;
import com.sparta.plus.dto.response.PostGetResponse;
import com.sparta.plus.security.CustomUserDetails;
import com.sparta.plus.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostController {
	private final PostService postService;

	@PostMapping
	public ResponseEntity<BaseResponse<Object>> create(@Valid @RequestBody PostRequest request,
		Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		PostCreateResponse response = postService.create(request, userDetails.getUser());

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(
				BaseResponse.builder()
					.msg("게시글 생성 성공")
					.data(response)
					.build()
			);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<BaseResponse<Object>> get(@PathVariable Long postId) {
		PostGetResponse response = postService.get(postId);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(
				BaseResponse.builder()
					.msg("게시글 상세조회 성공")
					.data(response)
					.build()
			);
	}
}
