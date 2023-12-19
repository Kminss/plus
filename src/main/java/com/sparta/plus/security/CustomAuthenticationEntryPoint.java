package com.sparta.plus.security;

import static com.sparta.plus.exception.ErrorCode.*;
import static java.nio.charset.StandardCharsets.*;
import static org.springframework.http.MediaType.*;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.plus.dto.BaseResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Jwt 인증 실패")
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {
		log.info("로그인 인증 실패");
		setResponseConfig(response);
		objectMapper
			.registerModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.writeValue(response.getWriter(),
				BaseResponse.builder()
					.msg(UNAUTHORIZED.getMessage())
					.build()
			);
	}

	private void setResponseConfig(HttpServletResponse response) {
		response.setContentType(APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(UTF_8.name());
		response.setStatus(UNAUTHORIZED.getStatus());
	}
}
