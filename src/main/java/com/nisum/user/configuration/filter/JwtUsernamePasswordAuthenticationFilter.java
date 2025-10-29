package com.nisum.user.configuration.filter;


import java.io.IOException;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.user.configuration.security.JwtConfiguration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

public final class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtConfiguration jwtConfiguration;
	private final SecretKey secretKey;

	private JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
													JwtConfiguration jwtConfiguration,
													SecretKey secretKey) {
		this.authenticationManager = authenticationManager;
		this.jwtConfiguration = jwtConfiguration;
		this.secretKey = secretKey;
	}

	public static JwtUsernamePasswordAuthenticationFilter getInstance(AuthenticationManager authenticationManager,
																	  JwtConfiguration jwtConfiguration,
																	  SecretKey secretKey) {
		return new JwtUsernamePasswordAuthenticationFilter(authenticationManager, jwtConfiguration, secretKey);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response) throws AuthenticationException {

		try {

			UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper().readValue(request.getInputStream(),
					UsernameAndPasswordAuthenticationRequest.class);

			// System.out.println(authenticationRequest.getUsername()+"   - --   "+authenticationRequest.getPassword());

			Authentication authentication = new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(),
					authenticationRequest.getPassword()
			);

			return authenticationManager.authenticate(authentication);

		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
											HttpServletResponse response,
											FilterChain chain,
											Authentication authResult) throws IOException, ServletException {

		// String token = Jwts.builder()
		// 		.subject(authResult.getName())
		// 		.claim("authorities", authResult.getAuthorities())
		// 		.issuedAt(new Date())
		// 		.expiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.getTokenExpirationAfterDays())))
		// 		.signWith(secretKey, Jwts.SIG.HS256)
		// 		.compact();

		// response.addHeader(jwtConfiguration.getAuthorizationHeader(), jwtConfiguration.getTokenPrefix()+" "+token);

	}

    @NoArgsConstructor
    @Data
    public class UsernameAndPasswordAuthenticationRequest {

        private String username;
        private String password;

    }
}
