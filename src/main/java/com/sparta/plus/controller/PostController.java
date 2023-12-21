package com.sparta.plus.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.plus.dto.BaseResponse;
import com.sparta.plus.dto.request.PostRequest;
import com.sparta.plus.dto.response.PostCreateResponse;
import com.sparta.plus.dto.response.PostGetResponse;
import com.sparta.plus.dto.response.PostReadResponse;
import com.sparta.plus.dto.response.PostUpdateResponse;
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
	public ResponseEntity<BaseResponse<Object>> create(
		@Valid @RequestBody PostRequest request,
		Authentication authentication
	) {
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

	@GetMapping
	public ResponseEntity<BaseResponse<Object>> read(
		@PageableDefault(size = 5, sort = "createdDateTime", direction = Sort.Direction.DESC)
		Pageable pageable
	) {
		Page<PostReadResponse> response = postService.read(pageable);

		return ResponseEntity.status(HttpStatus.OK)
			.body(
				BaseResponse.builder()
					.msg("게시글 목록 조회 성공")
					.data(response)
					.build()
			);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<BaseResponse<Object>> get(@PathVariable Long postId) {
		PostGetResponse response = postService.get(postId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(
				BaseResponse.builder()
					.msg("게시글 상세조회 성공")
					.data(response)
					.build()
			);
	}

	@PutMapping("/{postId}")
	public ResponseEntity<BaseResponse<Object>> update(
		@PathVariable Long postId,
		@Valid @RequestBody PostRequest request,
		Authentication authentication
	) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		PostUpdateResponse response = postService.update(postId, request, userDetails.getUser());

		return ResponseEntity.status(HttpStatus.OK)
			.body(
				BaseResponse.builder()
					.msg("게시글 수정 성공")
					.data(response)
					.build()
			);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<BaseResponse<Object>> delete(
		@PathVariable Long postId,
		Authentication authentication
	) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		postService.delete(postId, userDetails.getUser());

		return ResponseEntity.status(HttpStatus.OK)
			.body(
				BaseResponse.builder()
					.msg("게시글 삭제 성공")
					.build()
			);
	}
}
