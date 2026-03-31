package com.team.todolist.todo.repository;

import com.team.todolist.todo.entity.Todo;
import com.team.todolist.user.entity.User;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

	List<Todo> findAllByUser(User user);
}
