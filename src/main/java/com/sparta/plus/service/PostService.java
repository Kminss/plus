package com.sparta.plus.service;

import com.sparta.plus.dto.request.PostRequest;
import com.sparta.plus.dto.response.PostCreateResponse;
import com.sparta.plus.dto.response.PostGetResponse;
import com.sparta.plus.dto.response.PostUpdateResponse;
import com.sparta.plus.entity.User;

public interface PostService {

	/**
	 * 게시글 생성 메소드
	 *
	 * @param request     - 게시글 생성 요청 dto
	 * @param loginUser - 로그인 인증 유저
	 * @return PostCreateResponse - 게시글 생성 응답 dto
	 */
	PostCreateResponse create(PostRequest request, User loginUser);

	/**
	 * 게시글 상세조회 메소드
	 * @param postId - 게시글 식별자 ID
	 * @return PostResponse - 게시글 조회 응답 dto
	 */
	PostGetResponse get(Long postId);

	/**
	 * 게시글 수정 메소드
	 *
	 * @param postId - 게시글 식별자 ID
	 * @param request - 게시글 수정 요청 dto
	 * @param loginUser - 로그인 인증 유저
	 * @return PostUpdateResponse - 게시글 수정 응답 dto
	 */
	PostUpdateResponse update(Long postId, PostRequest request, User loginUser);

	/**
	 * 게시글 삭제 메소드
	 *
	 * @param postId - 게시글 식별자 ID
	 * @param loginUser - 로그인 인증 유저
	 */
	void delete(Long postId, User loginUser);
}
