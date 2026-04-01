package com.team.todolist.todo.dto;
 
import com.team.todolist.todo.entity.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoUpdateDto {
 
    private String content;
    private TodoStatus status;
}
 