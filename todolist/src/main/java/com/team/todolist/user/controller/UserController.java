package com.team.todolist.user.controller;

import com.team.todolist.security.auth.CustomUserDetails;
import com.team.todolist.user.dto.JoinRequestDto;
import com.team.todolist.user.entity.User;
import com.team.todolist.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
            return "redirect:/login?joined";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "join";
        }
    }

    // 회원탈퇴
    @PostMapping("/users/delete")
    public String deleteUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        User user = userDetails.getUser();

        // 회원탈퇴 처리
        userService.deleteUser(user);

        // 세션 무효화 (로그아웃 처리)
        new SecurityContextLogoutHandler()
                .logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        return "redirect:/login?deleted";
    }
}
