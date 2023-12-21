package com.sparta.plus.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.plus.entity.Post;
import com.sparta.plus.repository.PostRepository;
import com.sparta.plus.service.PostScheduleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "게시글 스케줄러 서비스")
@Service
@RequiredArgsConstructor
public class PostScheduleServiceImpl implements PostScheduleService {
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
	private final PostRepository postRepository;

	@Override
	@Transactional
	@Scheduled(cron = "0 0 */1 * * *")
	public void deletePostsSchedule() {
		// 포맷 적용
		String formattedDateTime = LocalDateTime.now().format(DATE_TIME_FORMAT);
		// 문자열을 LocalDateTime 으로 변환
		LocalDateTime parsedDateTime = LocalDateTime.parse(formattedDateTime, DATE_TIME_FORMAT);

		//현재 시간까지 삭제범위에 포함해야하므로 +1시간 하여 Before
		parsedDateTime = parsedDateTime
			.minusDays(90)
			.plusHours(1);

		log.info("삭제 기준 일시 : {}", parsedDateTime);
		List<Post> posts = postRepository.findAllByModifiedDateTimeBefore(parsedDateTime);

		if (!posts.isEmpty()) {
			postRepository.deleteAllInBatch(posts);
		}
	}
}