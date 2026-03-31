package com.team.todolist.todo.service;

import java.util.List;

import com.team.todolist.todo.entity.Todo;
import com.team.todolist.user.entity.User;

public class TodoService {

	// 내 Todo만 조회
	public List<Todo> getTodos(User user) {
        return todoRepository.findByUser(user);
    }

	//생성 시 user 연결
	public void create(String content, User user) {
        Todo todo = new Todo();
        todo.setContent(content);
        todo.setCompleted(false);
        todo.setUser(user); // 핵심

        todoRepository.save(todo);
    }
}
