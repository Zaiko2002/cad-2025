package com.example.lab5;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder().username("user").password(passwordEncoder.encode("password")).roles("USER").build();
        UserDetails moderator = User.builder().username("moderator").password(passwordEncoder.encode("password")).roles("MODERATOR").build();
        return new InMemoryUserDetailsManager(user, moderator);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключаем CSRF для работы с API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated() // API требует авторизации
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()) // Включаем Basic Auth
                .formLogin(Customizer.withDefaults());
        return http.build();
    }
}