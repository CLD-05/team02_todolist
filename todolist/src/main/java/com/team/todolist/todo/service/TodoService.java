package com.team.todolist.todo.service;

<<<<<<< Updated upstream
import com.team.todolist.todo.dto.TodoUpdateDto;
import com.team.todolist.todo.dto.TodoRequestDto;
import com.team.todolist.todo.dto.TodoResponseDto;
import com.team.todolist.todo.entity.Todo;
import com.team.todolist.todo.repository.TodoRepository;
import com.team.todolist.user.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
=======
import java.util.List;

>>>>>>> Stashed changes
import org.springframework.stereotype.Service;

<<<<<<< Updated upstream
import java.time.LocalDateTime;
import java.util.List;
=======
import com.team.todolist.todo.dto.TodoRequestDto;
import com.team.todolist.todo.dto.TodoResponseDto;
import com.team.todolist.todo.dto.TodoUpdateDto;
import com.team.todolist.todo.entity.Category;
import com.team.todolist.todo.entity.Todo;
import com.team.todolist.todo.repository.CategoryRepository;
import com.team.todolist.todo.repository.TodoRepository;
import com.team.todolist.user.entity.User;

import lombok.RequiredArgsConstructor;
>>>>>>> Stashed changes

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;
    
    // 일정 생성
    public TodoResponseDto createTodo(User user, TodoRequestDto requestDto) {
<<<<<<< Updated upstream
        Todo todo = new Todo(
                user,
                requestDto.getContent()
        );

=======

        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        Todo todo = new Todo(user, requestDto.getContent(), category);
        
>>>>>>> Stashed changes
        Todo savedTodo = todoRepository.save(todo);
        
        return new TodoResponseDto(savedTodo);
    }
    // 내 일정 전체 조회
    public List<TodoResponseDto> getTodos(User user) {
        return todoRepository.findAllByUser(user).stream()
                .map(TodoResponseDto::new)
                .toList();
    }

    // 일정 단건 조회
    public TodoResponseDto getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        return new TodoResponseDto(todo);
    }

    // 일정 수정
    public TodoResponseDto updateTodo(Long todoId, TodoUpdateDto requestDto) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        // content 수정 (값 있을 때만)
        if (requestDto.getContent() != null) {
            todo.updateContent(requestDto.getContent());
        }

        // status 수정 (값 있을 때만)
        if (requestDto.getStatus() != null) {
            todo.updateStatus(requestDto.getStatus());
        }

        return new TodoResponseDto(todo);
    }

    // 일정 삭제
    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        todoRepository.delete(todo);
    }
<<<<<<< Updated upstream
}
=======

	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}
}
>>>>>>> Stashed changes
