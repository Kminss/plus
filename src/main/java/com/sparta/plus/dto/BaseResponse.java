package com.sparta.plus.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@Builder
@RequiredArgsConstructor
public class BaseResponse<T> {
	private final String msg;
	private final T data;

}
