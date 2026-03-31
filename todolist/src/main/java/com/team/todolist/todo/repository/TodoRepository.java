package com.team.todolist.todo.repository;

import java.util.List;

import com.team.todolist.todo.entity.Todo;
import com.team.todolist.user.entity.User;

public interface TodoRepository {
	List<Todo> findByUser(User user);
}
