package com.sparta.plus.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sparta.plus.config.JwtProperties;
import com.sparta.plus.dto.TokenDto;
import com.sparta.plus.entity.UserRoleEnum;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j(topic = "Jwt 유틸")
@RequiredArgsConstructor
@Component
public class JwtUtils {
	private static final String BEARER_PREFIX = "Bearer ";
	private static final String AUTHORIZATION_KEY = "auth";
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String REFRESH_TOKEN_HEADER = "RefreshToken";
	// 사용자 권한 값의 KEY
	private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
	private final JwtProperties jwtProperties;
	private String secretKey;
	private Long accessTokenExpiration;
	private Long refreshTokenExpiration;
	private Key key;

	@PostConstruct
	public void init() {
		log.info("secret key >>>>>> {}", secretKey);
		secretKey = jwtProperties.getSecretKey();
		accessTokenExpiration = jwtProperties.getAccessTokenExpiration();
		refreshTokenExpiration = jwtProperties.getRefreshTokenExpiration();

		byte[] bytes = Base64.getDecoder().decode(secretKey);
		key = Keys.hmacShaKeyFor(bytes);
	}

	private String substringToken(String tokenValue) {
		if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
			return tokenValue.substring(7);
		}

		log.error("Can not Substring Token Value");
		throw new IllegalArgumentException();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException | SignatureException e) {
			log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token, 만료된 JWT token 입니다.");
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
		} catch (IllegalArgumentException e) {
			log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
		}
		return false;
	}

	public TokenDto generateToken(String nickname, UserRoleEnum role) {
		Date date = new Date();

		String accessToken =
			Jwts.builder()
				.setSubject(nickname) // 사용자 식별자값(ID)
				.claim(AUTHORIZATION_KEY, role) // 사용자 권한
				.setExpiration(new Date(date.getTime() + accessTokenExpiration)) // 만료 시간
				.setIssuedAt(date) // 발급일
				.signWith(key, SIGNATURE_ALGORITHM) // 암호화 알고리즘
				.compact();

		String refreshToken =
			Jwts.builder()
				.setSubject(nickname) // 사용자 식별자값(ID)
				.claim(AUTHORIZATION_KEY, role) // 사용자 권한
				.setExpiration(new Date(date.getTime() + refreshTokenExpiration)) // 만료 시간
				.setIssuedAt(date) // 발급일
				.signWith(key, SIGNATURE_ALGORITHM) // 암호화 알고리즘
				.compact();

		return TokenDto.of(accessToken, refreshToken);
	}

	public void setTokenForCookie(TokenDto tokenDto, HttpServletResponse response) {
		String accessToken = URLEncoder.encode(BEARER_PREFIX + tokenDto.getAccessToken(), StandardCharsets.UTF_8)
			.replaceAll("\\+", "%20");

		String refreshToken = URLEncoder.encode(BEARER_PREFIX + tokenDto.getRefreshToken(), StandardCharsets.UTF_8)
			.replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

		Cookie accessTokenCookie = makeTokenCookie(AUTHORIZATION_HEADER, accessToken);
		Cookie refreshTokenCookie = makeTokenCookie(REFRESH_TOKEN_HEADER, refreshToken);

		// Response 객체에 Cookie 추가
		response.addCookie(accessTokenCookie);
		response.addCookie(refreshTokenCookie);
	}

	private Cookie makeTokenCookie(String header, String value) {
		Cookie cookie = new Cookie(header, value);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");

		return cookie;
	}

	public String getTokenFromCookie(String header, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}

		return Arrays.stream(cookies)
			.filter(cookie -> header.equals(cookie.getName()))
			.findFirst()
			.map(cookie -> URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8))
			.map(this::substringToken)
			.orElse("");
	}

	public String getAccessTokenHeader() {
		return AUTHORIZATION_HEADER;
	}

	public Claims getUserInfoFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
}
