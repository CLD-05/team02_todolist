package com.team.todolist.user.service;

import com.team.todolist.todo.repository.TodoRepository;
import com.team.todolist.user.dto.JoinRequestDto;
import com.team.todolist.user.entity.User;
import com.team.todolist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public String join(JoinRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role("ROLE_USER")
                .build();

        userRepository.save(user);
        return "회원가입 완료";
    }

    // 회원탈퇴 (해당 유저의 Todo 전부 삭제 후 유저 삭제)
    @Transactional
    public void deleteUser(User user) {
        // 유저의 Todo 먼저 전부 삭제
        todoRepository.deleteAllByUser(user);
        // 유저 삭제
        userRepository.delete(user);
    }
}
