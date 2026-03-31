package com.team.todolist.user.service;

import com.team.todolist.user.dto.JoinRequestDto;
import com.team.todolist.user.dto.LoginUserDto;
import com.team.todolist.user.entity.User;
import com.team.todolist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public String join(JoinRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일이다.");
        }

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);
        return "회원가입 완료";
    }

    public String login(LoginUserDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일"));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않음");
        }

        return "로그인 성공";
    }
}