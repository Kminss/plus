package com.sparta.plus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.plus.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByNickname(String nickname);

	Optional<User> findByNickname(String nickname);
}
