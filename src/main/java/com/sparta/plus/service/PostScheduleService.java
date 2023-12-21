package com.sparta.plus.service;

public interface PostScheduleService {
	/**
	 * 마지막 수정일이 90일이 지난 게시글 삭제
	 * 스케쥴 동작은 매 시간
	 * 스케쥴러 동작시 현재 일,시 (yyyy-mm-dd HH) 기준으로 90일 지난 데이터 삭제
	 * 현재 일시는 LocalTime (KST) 기준
	 * @Scheduled 의 `zone` 속성으로 타임존을 설정할 수가 있음 -> 서버와 다른 타임존 사용하고 싶을 때
	 */
	void deletePostsSchedule();
}
