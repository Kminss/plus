package com.sparta.plus.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 25)
	private String nickname;
	@Column
	private String password;

	@Column
	@Enumerated(EnumType.STRING)
	private UserRoleEnum role;

	public User(String nickname, String password, UserRoleEnum role) {
		this.nickname = nickname;
		this.password = password;
		this.role = role;
		this.createdBy = nickname;
		this.modifiedBy = nickname;
	}

	public static User of(String nickname, String encodedPassword, UserRoleEnum role) {
		return new User(nickname, encodedPassword, role);
	}
}

