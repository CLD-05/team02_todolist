package com.team.todolist.todo.dto;

import com.team.todolist.todo.entity.TodoStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoUpdateDto {

    private String content;
    private TodoStatus status;

    // todolist1 호환용 생성자
    public TodoUpdateDto(String content, TodoStatus status) {
        this.content = content;
        this.status = status;
    }
}
