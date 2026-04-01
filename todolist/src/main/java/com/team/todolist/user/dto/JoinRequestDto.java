package com.team.todolist.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;  // ← 추가

@Getter
@Setter  // ← 추가
public class JoinRequestDto {
	
	

    @Email(message = "올바른 이메일 형식으로 입력해주세요.")
    @NotBlank(message = "이메일은 필수이다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
//    @Size(min = 8, max = 20,message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
//    @Pattern(
//    		regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,20}$",
//    		message = "비밀번호는 영문과 숫자를 포함해야 합니다."
//    )
    private String password;
    
    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String passwordConfirm;
}