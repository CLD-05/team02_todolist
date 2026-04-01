package com.team.todolist.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.team.todolist.todo.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
