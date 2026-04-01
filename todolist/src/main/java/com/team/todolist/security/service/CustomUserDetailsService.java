package com.team.todolist.security.service;

import com.team.todolist.security.auth.CustomUserDetails;
import com.team.todolist.user.entity.User;
import com.team.todolist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. UserRepository를 통해 DB에서 이메일로 유저를 찾습니다.
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일을 가진 사용자를 찾을 수 없습니다: " + email));

        // 2. 찾은 유저(User)를 시큐리티 전용 유저(CustomUserDetails)로 변신시켜서 반환합니다.
        return new CustomUserDetails(user);
    }
}