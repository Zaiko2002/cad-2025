package com.example.lab4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Замена устаревшей @EnableGlobalMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        // 1. Обычный пользователь (может только смотреть и добавлять)
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

        // 2. Модератор (может всё)
        UserDetails moderator = User.builder()
                .username("moderator")
                .password(passwordEncoder.encode("password"))
                .roles("MODERATOR")
                .build();

        // 3. Студент (доп. задание: может менять статус задач)
        UserDetails student = User.builder()
                .username("student")
                .password(passwordEncoder.encode("password"))
                .roles("STUDENT")
                .build();

        // 4. Работник (доп. задание: может удалять задачи)
        UserDetails worker = User.builder()
                .username("worker")
                .password(passwordEncoder.encode("password"))
                .roles("WORKER")
                .build();

        return new InMemoryUserDetailsManager(user, moderator, student, worker);
    }
}