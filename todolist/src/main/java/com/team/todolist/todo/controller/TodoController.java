package com.team.todolist.todo.controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.team.todolist.security.auth.CustomUserDetails;

@AuthenticationPrincipal CustomUserDetails userDetails
public class TodoController {

	User user = null;
	if (userDetails != null) {
	    user = userDetails.getUser();
	}
	
	User user = null;
	if (userDetails != null) {
	    user = userDetails.getUser();
	}
}
