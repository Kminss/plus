package com.sparta.plus.service;

import com.sparta.plus.dto.request.CommentRequest;
import com.sparta.plus.dto.response.CommentCreateResponse;
import com.sparta.plus.dto.response.CommentUpdateResponse;
import com.sparta.plus.entity.User;

public interface CommentService {
	/**
	 * 댓글 생성 메소드
	 *
	 * @param postId - 댓글이 달린 게시글 식별자 ID
	 * @param request - 댓글 생성 요청 dto
	 * @param user - 로그인 인증 유저
	 * @return CommentCreateResponse - 댓글 생성 응답 dto
	 */
	CommentCreateResponse create(Long postId, CommentRequest request, User user);

	/**
	 * 댓글 수정 메소드
	 *
	 * @param postId - 댓글이 달린 게시글 식별자 ID
	 * @param commentId - 댓글 식별자 ID
	 * @param request - 댓글 수정 요청 dto
	 * @param user - 로그인 인증 유저
	 * @return CommentUpdateResponse - 댓글 수정 응답 dto
	 */

	CommentUpdateResponse update(Long postId, Long commentId, CommentRequest request, User user);

	/**
	 * 댓글 삭제 메소드
	 *
	 * @param postId -  댓글이 달린 게시글 식별자 ID
	 * @param commentId - 댓글 식별자 ID
	 * @param user - 로그인 인증 유저
	 */
	void delete(Long postId, Long commentId, User user);
}
