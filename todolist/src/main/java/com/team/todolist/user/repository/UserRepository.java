package com.team.todolist.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team.todolist.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
}
