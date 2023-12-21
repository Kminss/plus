package com.sparta.plus.repository.querydsl;

import static com.sparta.plus.entity.QPost.*;
import static com.sparta.plus.entity.QUser.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.plus.dto.response.PostReadResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<PostReadResponse> getPosts(Pageable pageable) {
		List<PostReadResponse> posts = queryFactory.select(
				Projections.constructor(PostReadResponse.class,
					post.id,
					post.user.nickname,
					post.title,
					post.createdDateTime
				))
			.from(post)
			.innerJoin(post.user, user)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(sortPost(pageable))
			.fetch();

		JPAQuery<Long> countQuery = queryFactory.select(post.count())
			.from(post);

		//getPage -> count 쿼리 지연
		// 1. 첫 번째 페이지이면서 content 크기가 한 페이지의 사이즈보다 작을 때 (ex, content:3개, page 크기: 10)
		// 2. 마지막 페이지일 때 (getOffset이 0이 아니면서, content 크기가 한 페이지의 사이즈보다 작을 때)
		return PageableExecutionUtils.getPage(posts, pageable, countQuery::fetchOne);
	}

	private OrderSpecifier<?> sortPost(Pageable pageable) {
		OrderSpecifier<?> orderSpecifier = null;
		for (Sort.Order o : pageable.getSort()) {
			Order direction = o.isAscending() ? Order.ASC : Order.DESC;
			orderSpecifier = switch (o.getProperty().toUpperCase()) {
				case "NICKNAME" -> new OrderSpecifier<>(direction, post.user.nickname);
				case "TITLE" -> new OrderSpecifier<>(direction, post.title);
				default -> new OrderSpecifier<>(direction, post.createdDateTime);
			};
		}
		return orderSpecifier;
	}
}
