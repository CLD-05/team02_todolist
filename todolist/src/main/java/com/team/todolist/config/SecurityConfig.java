package com.team.todolist.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.team.todolist.security.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class SecurityConfig {
	private final CustomUserDetailsService customUserDetailsService;
	

	http.userDetailsService(customUserDetailsService);
	
}
