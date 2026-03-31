package com.team.todolist.security.auth;

import com.team.todolist.user.entity.User; // 임포트 확인!
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
         
        authorities.add(new SimpleGrantedAuthority(user.getRole())); 
        
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // 로그인 시 아이디로 사용할 필드 (이메일)
    }

    // 계정 상태 설정 (기본값 true)
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    // 나중에 컨트롤러나 서비스에서 현재 로그인한 유저 객체를 바로 꺼내 쓰기 위한 도우미 메서드
    public User getUser() {
        return user;
    }
}