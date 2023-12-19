package com.sparta.plus.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BaseResponse<T> {
	private final String msg;
	private final T data;

	public BaseResponse(String msg, T data) {
		if (data == null) {
			data = (T) "";
		}
		this.msg = msg;
		this.data = data;
	}
}
