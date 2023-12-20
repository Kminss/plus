package com.sparta.plus.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.plus.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
