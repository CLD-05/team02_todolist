package com.team.todolist.user.repository;

import java.util.Optional;

import com.team.todolist.user.entity.User;

public interface UserRepository {

	Optional<User> findByEmail(String email);
}
