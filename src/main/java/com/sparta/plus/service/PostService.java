package com.sparta.plus.service;

import com.sparta.plus.dto.request.PostRequest;
import com.sparta.plus.dto.response.PostCreateResponse;
import com.sparta.plus.entity.User;

public interface PostService {

	/**
	 * 게시글 생성 메서드
	 *
	 * @param request     - 게시글 생성 요청 dto
	 * @param user - 로그인 유저 객체
	 * @return PostCreateResponse - 게시글 생성 응답 dto
	 */
	PostCreateResponse create(PostRequest request, User user);
}
