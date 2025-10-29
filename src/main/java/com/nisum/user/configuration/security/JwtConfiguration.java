package com.nisum.user.configuration.security;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.jwt")
@Configuration
public class JwtConfiguration {

	private String secretKey;
	private String tokenPrefix;
	private Integer tokenExpirationAfterDays;

	public String getAuthorizationHeader() {
		return HttpHeaders.AUTHORIZATION;
	}

	@Bean
	@Primary
	public SecretKey secretKeyForSignIn() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}

}