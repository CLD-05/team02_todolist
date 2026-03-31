package com.team.todolist.user.controller;

import com.team.todolist.user.dto.JoinRequestDto;
import com.team.todolist.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 로그인 페이지
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // 회원가입 페이지
    @GetMapping("/users/join")
    public String joinForm() {
        return "join";
    }

    // 회원가입 처리
    @PostMapping("/users/join")
    public String join(@Valid @ModelAttribute JoinRequestDto requestDto, Model model) {
        try {
            userService.join(requestDto);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "join";
        }
    }
}