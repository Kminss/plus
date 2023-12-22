package com.sparta.plus.entity;

import com.sparta.plus.dto.request.CommentRequest;

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
@Table(name = "comments")
@Entity
public class Comment extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 1000)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	public Comment(String content, Post post, User user) {
		this.content = content;
		this.post = post;
		this.user = user;
	}

	public static Comment of(String content, Post post, User user) {
		return new Comment(content, post, user);
	}

	public void update(CommentRequest request) {
		this.content = request.getContent();
	}
}
