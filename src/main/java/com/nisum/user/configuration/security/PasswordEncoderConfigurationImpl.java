package com.nisum.user.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfigurationImpl {

	@Bean
	public PasswordEncoder getBeanPasswordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}