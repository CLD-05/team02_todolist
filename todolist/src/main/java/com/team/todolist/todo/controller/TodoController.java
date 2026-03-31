package com.team.todolist.todo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.team.todolist.common.response.ApiResponse;
import com.team.todolist.todo.dto.TodoRequestDto;
import com.team.todolist.todo.dto.TodoResponseDto;
import com.team.todolist.todo.dto.TodoUpdateDto;
import com.team.todolist.todo.service.TodoService;
import com.team.todolist.user.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // 일정 생성
    @PostMapping
    public ApiResponse<TodoResponseDto> createTodo(
            @RequestBody TodoRequestDto requestDto
    ) {
        // TODO: 나중에 로그인 사용자 정보로 교체
        User user = null;

        TodoResponseDto response = todoService.createTodo(user, requestDto);
        return ApiResponse.success("일정 생성 성공", response);
    }

    // 전체 일정 조회
    @GetMapping
    public ApiResponse<List<TodoResponseDto>> getTodos() {
        // TODO: 나중에 로그인 사용자 정보로 교체
        User user = null;

        List<TodoResponseDto> response = todoService.getTodos(user);
        return ApiResponse.success("일정 목록 조회 성공", response);
    }

    // 단건 일정 조회
    @GetMapping("/{todoId}")
    public ApiResponse<TodoResponseDto> getTodo(@PathVariable Long todoId) {
        TodoResponseDto response = todoService.getTodo(todoId);
        return ApiResponse.success("일정 단건 조회 성공", response);
    }

    // 일정 수정
    @PatchMapping("/{todoId}")
    public ApiResponse<TodoResponseDto> updateTodo(
            @PathVariable Long todoId,
            @RequestBody TodoUpdateDto requestDto
    ) {
        TodoResponseDto response = todoService.updateTodo(todoId, requestDto);
        return ApiResponse.success("일정 수정 성공", response);
    }

    // 일정 삭제
    @DeleteMapping("/{todoId}")
    public ApiResponse<Void> deleteTodo(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
        return ApiResponse.success("일정 삭제 성공", null);
    }
}