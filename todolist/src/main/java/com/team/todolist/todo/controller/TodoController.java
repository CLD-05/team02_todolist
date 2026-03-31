package com.team.todolist.todo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team.todolist.common.response.ApiResponse;
import com.team.todolist.security.auth.CustomUserDetails;
import com.team.todolist.todo.dto.TodoRequestDto;
import com.team.todolist.todo.dto.TodoResponseDto;
import com.team.todolist.todo.service.TodoService;
import com.team.todolist.user.entity.User;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("/todos")
public class TodoController {

	private final TodoService todoService;
	
	@PostMapping
	public ApiResponse<TodoResponseDto> createTodo(
	        @AuthenticationPrincipal CustomUserDetails userDetails,
	        @RequestBody TodoRequestDto requestDto
	) {
	    User user = null;

	    if (userDetails != null) {
	        user = userDetails.getUser();
	    }

	    TodoResponseDto response = todoService.createTodo(user, requestDto);
	    return ApiResponse.success("일정 생성 성공", response);
	}
	
	@GetMapping
	public ApiResponse<List<TodoResponseDto>> getTodos(
	        @AuthenticationPrincipal CustomUserDetails userDetails
	) {
	    User user = null;

	    if (userDetails != null) {
	        user = userDetails.getUser();
	    }

	    List<TodoResponseDto> response = todoService.getTodos(user);
	    return ApiResponse.success("일정 목록 조회 성공", response);
	}
}