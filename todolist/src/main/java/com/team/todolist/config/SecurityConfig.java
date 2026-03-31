package com.team.todolist.config;

import com.team.todolist.security.service.CustomUserDetailsService; //  추가
import lombok.RequiredArgsConstructor; //  추가
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor //  추가
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService; //  추가

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(authorize -> authorize
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

                .requestMatchers(
                    "/api/users/join",
                    "/api/users/login"
                ).permitAll()

                .requestMatchers("/todos/**").authenticated()
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
            )

            //  추가 (핵심)
            .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}