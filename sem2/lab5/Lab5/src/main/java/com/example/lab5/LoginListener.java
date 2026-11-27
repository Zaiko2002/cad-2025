package com.example.lab5;;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();

        // Находим или создаем пользователя в БД
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setEmail(username + "@example.com");
            user.setPassword("hashed_password");
            userRepository.save(user);
        }

        LoginHistory history = new LoginHistory();
        history.setLoginTime(LocalDateTime.now());
        history.setUser(user);

        // Упрощенный IP-адрес для локальной разработки
        history.setIpAddress("127.0.0.1");

        loginHistoryRepository.save(history);
    }
}