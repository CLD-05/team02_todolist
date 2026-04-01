package com.team.todolist.todo.dto;

import java.time.LocalDateTime;
import com.team.todolist.todo.entity.Todo;
import com.team.todolist.todo.entity.TodoStatus;

import lombok.Getter;

@Getter
public class TodoResponseDto {

    private Long id;
    private String content;
    private TodoStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.content = todo.getContent();
        this.status = todo.getStatus();
        this.createdAt = todo.getCreatedAt();
        this.updatedAt = todo.getUpdatedAt();
    }
}