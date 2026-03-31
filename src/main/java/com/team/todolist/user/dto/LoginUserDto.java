package com.team.todolist.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginUserDto {

    @Email(message = "올바른 이메일 형식이어야 한다.")
    @NotBlank(message = "이메일은 필수이다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수이다.")
    private String password;
}