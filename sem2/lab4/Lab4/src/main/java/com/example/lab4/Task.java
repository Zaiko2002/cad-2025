package com.example.lab4;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String category; // Задание 1
    private String priority; // Задание 1
    private LocalDateTime createdAt; // Задание 1
    private boolean completed;

    // Задание 3: Связь с пользователем
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Задание 2: Связь с подзадачами
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubTask> subTasks = new ArrayList<>();

    // Задание 5: История изменений
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskHistory> histories = new ArrayList<>();

    public Task() {
        this.createdAt = LocalDateTime.now();
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public List<SubTask> getSubTasks() { return subTasks; }
    public void setSubTasks(List<SubTask> subTasks) { this.subTasks = subTasks; }
    public List<TaskHistory> getHistories() { return histories; }
    public void setHistories(List<TaskHistory> histories) { this.histories = histories; }
}