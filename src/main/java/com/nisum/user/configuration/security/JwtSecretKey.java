package com.nisum.user.configuration.security;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtSecretKey {

	@Bean
	public SecretKey secretKeyForSignIn(JwtConfiguration jwtConfiguration) {
		return Keys.hmacShaKeyFor(jwtConfiguration.getSecretKey().getBytes());
	}

}
