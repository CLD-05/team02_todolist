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
import org.springframework.validation.BindingResult;
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
    public String joinForm(Model model) {
        model.addAttribute("joinRequestDto", new JoinRequestDto());
        return "join";
    }

    // 회원가입 처리
    @PostMapping("/users/join")
    public String join(
            @Valid @ModelAttribute("joinRequestDto") JoinRequestDto requestDto,
            BindingResult bindingResult,
            Model model
    ) {
        // 비밀번호 확인 검사
        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "password.mismatch", "비밀번호가 일치하지 않습니다.");
        }

        // DTO 검증 실패
        if (bindingResult.hasErrors()) {
            return "join";
        }

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
        userService.deleteUser(user);
        new SecurityContextLogoutHandler()
                .logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login?deleted";
    }
}