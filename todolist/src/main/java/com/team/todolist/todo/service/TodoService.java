package com.team.todolist.todo.service;

import com.team.todolist.todo.dto.TodoUpdateDto;
import com.team.todolist.todo.dto.TodoRequestDto;
import com.team.todolist.todo.dto.TodoResponseDto;
import com.team.todolist.todo.entity.Todo;
import com.team.todolist.todo.repository.TodoRepository;
import com.team.todolist.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional
    public TodoResponseDto createTodo(User user, TodoRequestDto requestDto) {
        Todo todo = new Todo(user, requestDto.getContent());
        Todo savedTodo = todoRepository.save(todo);
        return new TodoResponseDto(savedTodo);
    }

    public List<TodoResponseDto> getTodos(User user) {
        return todoRepository.findAllByUser(user).stream()
                .map(TodoResponseDto::new)
                .toList();
    }

    public TodoResponseDto getTodo(Long todoId, User user) {
        Todo todo = todoRepository.findByIdAndUser(todoId, user)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없거나 접근 권한이 없습니다."));
        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto updateTodo(Long todoId, User user, TodoUpdateDto requestDto) {
        Todo todo = todoRepository.findByIdAndUser(todoId, user)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없거나 접근 권한이 없습니다."));

        if (requestDto.getContent() != null) {
            todo.updateContent(requestDto.getContent());
        }
        if (requestDto.getStatus() != null) {
            todo.updateStatus(requestDto.getStatus());
        }

        return new TodoResponseDto(todo);
    }

    @Transactional
    public void deleteTodo(Long todoId, User user) {
        Todo todo = todoRepository.findByIdAndUser(todoId, user)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없거나 접근 권한이 없습니다."));
        todoRepository.delete(todo);
    }
}