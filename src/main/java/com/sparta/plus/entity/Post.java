package com.sparta.plus.entity;

import com.sparta.plus.dto.request.PostRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posts")
@Entity
public class Post extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 500)
	private String title;

	@Column(length = 5000)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	public Post(String title, String content, User user) {
		this.title = title;
		this.content = content;
		this.user = user;
	}

	public static Post of(String title, String content, User user) {
		return new Post(title, content, user);
	}

	public void update(PostRequest request) {
		this.title = request.getTitle();
		this.content = request.getContent();
	}
}
