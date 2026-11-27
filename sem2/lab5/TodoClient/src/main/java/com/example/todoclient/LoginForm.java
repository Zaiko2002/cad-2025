package com.example.todoclient;

import javax.swing.*;
import java.awt.*;
import java.util.Base64;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {
        setTitle("Авторизация");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 5, 5));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Войти");

        add(new JLabel("Логин:"));
        add(usernameField);
        add(new JLabel("Пароль:"));
        add(passwordField);
        add(new JPanel());
        add(loginButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            // Кодируем в Base64: username:password
            String authHeader = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

            // Открываем главное окно
            new TaskManagerForm(authHeader, username).setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}