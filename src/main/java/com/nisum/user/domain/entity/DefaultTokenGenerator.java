package com.nisum.user.domain.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.nisum.user.configuration.security.JwtConfiguration;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultTokenGenerator implements TokenGenerator {

    private final JwtConfiguration jwtConfiguration;
    private final SecretKey secretKey;

    @Override
    public String generate(String username) {

        String token = Jwts.builder()
				.subject(username)
				.claim("authorities", List.of())
				.issuedAt(new Date())
				.expiration(java.sql.Date.valueOf(LocalDate.now().plusDays(10)))
				.signWith(secretKey)
				.compact();
        
        return token;
    }
}
