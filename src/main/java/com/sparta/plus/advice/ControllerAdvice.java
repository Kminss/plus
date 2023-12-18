package com.sparta.plus.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sparta.plus.dto.BaseResponse;
import com.sparta.plus.exception.ApiException;
import com.sparta.plus.exception.ErrorCode;

@RestControllerAdvice
public class ControllerAdvice {
	@ExceptionHandler(ApiException.class)
	public ResponseEntity handleApiException(ApiException exception) {
		ErrorCode errorCode = exception.getErrorCode();
		return ResponseEntity.status(errorCode.getStatus()).body(
			BaseResponse.builder()
				.msg(errorCode.getMessage())
				.build()
		);
	}
}
