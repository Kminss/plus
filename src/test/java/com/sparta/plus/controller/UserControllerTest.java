package com.sparta.plus.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.plus.config.SecurityConfig;
import com.sparta.plus.dto.request.SignupRequest;
import com.sparta.plus.dto.response.SignupResponse;
import com.sparta.plus.exception.ApiException;
import com.sparta.plus.exception.ErrorCode;
import com.sparta.plus.service.UserService;

@DisplayName("회원 컨트롤러 테스트")
@EnableConfigurationProperties
@Import(SecurityConfig.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
	private static final String BASE_URL = "/api/v1/users";
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private UserService userService;

	@Test
	void 회원가입_정상_테스트() throws Exception {
		//Given
		SignupRequest request = SignupRequest.of("nickname1", "password", "password");
		given(userService.signup(any())).willReturn(new SignupResponse(1L));

		//When
		ResultActions actions = mvc.perform(
			post(BASE_URL + "/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.with(csrf())

		);

		//Then
		actions
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(
				jsonPath("$.data.id").value(1L)
			);
	}

	@Test
	void 회원가입_실패_테스트() throws Exception{
		//Given
		SignupRequest request = SignupRequest.of("nickname1", "password", "password11");
		given(userService.signup(any())).willThrow(new ApiException(ErrorCode.INVALID_PASSWORD));

		//When
		ResultActions actions = mvc.perform(
			post(BASE_URL + "/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.with(csrf())

		);

		//Then
		actions
			.andDo(print())
			.andExpect(status().is4xxClientError())
			.andExpect(jsonPath("$.msg").exists());
	}

	@Test
	void 닉네임_중복_테스트() throws Exception {
	    //Given
	    String nickname = "nickname";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>() {
		};
		params.put("nickname", List.of(nickname));
	    //When
		ResultActions actions = mvc.perform(
			get(BASE_URL + "/check-nickname")
				.params(params)
				.with(csrf())

		);
	    //Then
		actions
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.msg").exists());
	}


}