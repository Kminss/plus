package com.sparta.plus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.plus.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByNickname(String nickname);
}
