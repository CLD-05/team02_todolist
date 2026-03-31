package com.team.todolist.todo.dto;

import com.team.todolist.todo.entity.TodoStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoUpdateDto {

    private String content;
    private TodoStatus status;
}