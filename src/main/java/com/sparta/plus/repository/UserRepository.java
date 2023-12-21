package com.sparta.plus.repository;

import static com.sparta.plus.exception.ErrorCode.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.plus.entity.User;
import com.sparta.plus.exception.ApiException;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByNickname(String nickname);

	Optional<User> findByNickname(String nickname);

	default User getByNickname(String nickname) {
		return findByNickname(nickname).orElseThrow(() -> new ApiException(NOT_FOUND_USER));
	}
}
