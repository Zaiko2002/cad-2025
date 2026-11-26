package com.example.lab2;


public class Task {
    private static Long nextId = 1L; // Генератор ID
    private Long id;
    private String title;
    private String description;
    private String category; // Дополнительное задание: категория задачи
    private boolean completed;

    public Task() {
        this.id = generateId();
    }

    public Task(String title, String description, String category) {
        this.id = generateId();
        this.title = title;
        this.description = description;
        this.category = category;
        this.completed = false;
    }

    private synchronized Long generateId() {
        return nextId++;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", completed=" + completed +
                '}';
    }
}