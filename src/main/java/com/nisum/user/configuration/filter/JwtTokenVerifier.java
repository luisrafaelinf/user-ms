package com.nisum.user.configuration.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nisum.user.configuration.security.JwtConfiguration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public final class JwtTokenVerifier extends OncePerRequestFilter {

	private final JwtConfiguration jwtConfiguration;
	private final SecretKey secretKey;

	private JwtTokenVerifier(JwtConfiguration jwtConfiguration, SecretKey secretKey) {
		this.jwtConfiguration = jwtConfiguration;
		this.secretKey = secretKey;
	}

	public static JwtTokenVerifier getInstance(JwtConfiguration jwtConfiguration, SecretKey secretKey) {
		return new JwtTokenVerifier(jwtConfiguration, secretKey);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest,
									HttpServletResponse httpServletResponse,
									FilterChain filterChain) throws ServletException, IOException {

		String authorizationHeader = httpServletRequest.getHeader(jwtConfiguration.getAuthorizationHeader());

		if (Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith(jwtConfiguration.getTokenPrefix())) {

			filterChain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}

		String token = authorizationHeader.replace(jwtConfiguration.getTokenPrefix().trim(), "");

		if (Objects.isNull(token)) {
			
		}

		try {

			Jws<Claims> claimsJws = Jwts.parser()
					.verifyWith(secretKey)
                    .build()
					.parseSignedClaims(token);

			Claims body = claimsJws.getPayload();
			String username = body.getSubject();

			var authorities = (List<Map<String, String>>) body.get("authorities");
			List<SimpleGrantedAuthority> authority = authorities.stream()
					.map(m -> new SimpleGrantedAuthority(m.get("authority")))
					.collect(Collectors.toList());

			Authentication authentication = new UsernamePasswordAuthenticationToken(
					username,
					null,
					authority
			);

			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (JwtException ex) {

			httpServletRequest.setAttribute("exception", ex);
			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher("expired-token");
			dispatcher.forward(httpServletRequest, httpServletResponse);
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);

	}

}
