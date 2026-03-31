package com.team.todolist.todo.controller;

import com.team.todolist.todo.service.TodoService;
import com.team.todolist.user.entity.User;
import com.team.todolist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final UserRepository userRepository;

    //Todo 목록
    @GetMapping("/todos")
    public String todos(Model model, Authentication authentication) {

        String email = authentication.getName(); // 로그인 유저
        User user = userRepository.findByEmail(email).orElseThrow();

        model.addAttribute("todos", todoService.getTodos(user));

        return "todos";
    }

    //Todo 생성
    @PostMapping("/todos/new")
    public String create(@RequestParam String content,
                         Authentication authentication) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        todoService.create(content, user);

        return "redirect:/todos";
    }
}