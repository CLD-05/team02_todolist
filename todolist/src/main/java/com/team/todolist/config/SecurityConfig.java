package com.team.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 현재는 REST API 테스트와 초기 화면 테스트가 같이 필요하니까 임시로 비활성화
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(authorize -> authorize
                // 공개 페이지 / 정적 리소스
                .requestMatchers(
                    "/",
                    "/login",
                    "/users/join",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/test.html",
                    "/login-test.html",
                    "/join-test.html"
                ).permitAll()

                // 회원가입 / 로그인 API는 인증 없이 호출 가능해야 함
                .requestMatchers(
                    "/api/users/join",
                    "/api/users/login"
                ).permitAll()

                // Todo 기능은 로그인 필요
                .requestMatchers("/todos/**").authenticated()

                // 나머지는 일단 열어둘지 막을지 팀 기준으로 선택
                .anyRequest().permitAll()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/todos", true)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}