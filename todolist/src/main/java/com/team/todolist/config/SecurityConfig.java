package com.team.todolist.config;

import lombok.RequiredArgsConstructor;
import com.team.todolist.security.service.CustomUserDetailsService;
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;
	
	http.userDetailsService(customUserDetailsService);
}
