package com.sparta.plus.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sparta.plus.util.JwtUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws
		ServletException,
		IOException {
		String header = jwtUtils.getAccessTokenHeader();
		String tokenValue = jwtUtils.getTokenFromCookie(header, req);

		if (StringUtils.hasText(tokenValue) && jwtUtils.validateToken(tokenValue)) {
			Claims info = jwtUtils.getUserInfoFromToken(tokenValue);
			setAuthentication(info.getSubject());
		}

		filterChain.doFilter(req, res);
	}

	// 인증 처리
	public void setAuthentication(String username) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = createAuthentication(username);
		context.setAuthentication(authentication);

		SecurityContextHolder.setContext(context);
	}

	// 인증 객체 생성
	private Authentication createAuthentication(String username) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}