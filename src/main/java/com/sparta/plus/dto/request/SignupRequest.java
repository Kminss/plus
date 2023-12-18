package com.sparta.plus.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class SignupRequest {

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9]{3,}$", message = "영어 및 숫자로만 구성되고 3자 이상이어야합니다.")
	private final String nickname;

	@NotBlank
	@Size(min = 4, max = 50, message = "비밀번호는 최소 4자 이상, 50자 미만이여야합니다.")
	private final String password;
	@NotBlank
	@Size(min = 4, max = 50, message = "비밀번호는 최소 4자 이상, 50자 미만이여야합니다.")
	private final String confirmPassword;

	public static SignupRequest of(String nickname, String password, String confirmPassword) {
		return new SignupRequest(nickname, password, confirmPassword);
	}
}
