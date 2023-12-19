package com.sparta.plus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisProperties {
	private final String host;
	private final Integer port;

}
