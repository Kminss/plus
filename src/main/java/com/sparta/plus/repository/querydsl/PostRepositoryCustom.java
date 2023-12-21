package com.sparta.plus.repository.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.plus.dto.response.PostReadResponse;

public interface PostRepositoryCustom {
	Page<PostReadResponse> getPosts(Pageable pageable);
}
