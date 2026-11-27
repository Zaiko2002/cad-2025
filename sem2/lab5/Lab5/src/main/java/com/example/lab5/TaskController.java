package com.example.lab5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TaskController {

    @Autowired private TaskRepository taskRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private TaskHistoryRepository taskHistoryRepository;
    @Autowired private SubTaskRepository subTaskRepository;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "index";
    }

    @PostMapping("/addTask")
    public String addTask(@ModelAttribute Task task, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        task.setUser(user);
        taskRepository.save(task);
        return "redirect:/";
    }


    @GetMapping("/api/tasks")
    public ResponseEntity<List<Task>> getAllTasksApi() {
        return ResponseEntity.ok(taskRepository.findAll());
    }


    @PostMapping("/api/tasks")
    public ResponseEntity<Task> createTaskApi(@RequestBody Task task, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        task.setUser(user);
        task.setCreatedAt(LocalDateTime.now());
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.ok(savedTask);
    }


    @DeleteMapping("/api/tasks/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> deleteTaskApi(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}