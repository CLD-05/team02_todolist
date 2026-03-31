package com.team.todolist.user.controller;

import com.team.todolist.common.response.ApiResponse;
import com.team.todolist.user.dto.JoinRequestDto;
import com.team.todolist.user.dto.LoginUserDto;
import com.team.todolist.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ApiResponse<String> join(@Valid @RequestBody JoinRequestDto requestDto) {
        return ApiResponse.success(userService.join(requestDto));
    }

 //   @PostMapping("/login")
 //   public ApiResponse<String> login(@Valid @RequestBody LoginUserDto requestDto) {
 //       return ApiResponse.success(userService.login(requestDto));
 //   }
}