package com.team.todolist.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team.todolist.todo.entity.Todo;
import com.team.todolist.user.entity.User;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	
	List<Todo> findByUser(User user);
}
