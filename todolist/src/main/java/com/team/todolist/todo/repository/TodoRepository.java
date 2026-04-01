package com.team.todolist.todo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.todolist.todo.entity.Todo;
import com.team.todolist.user.entity.User;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

	List<Todo> findAllByUser(User user);
	
	Optional<Todo> findByIdAndUser(Long id, User user);
}