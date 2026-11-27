package com.example.lab4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskHistoryRepository taskHistoryRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "index";
    }

    @PostMapping("/addTask")
    public String addTask(@ModelAttribute Task task, java.security.Principal principal) {
        // Привязываем задачу к текущему пользователю
        User user = userRepository.findByUsername(principal.getName());
        task.setUser(user);
        taskRepository.save(task);
        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'WORKER')")
    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('MODERATOR', 'STUDENT')")
    @PostMapping("/updateTask/{id}")
    public String updateTask(@PathVariable Long id, @RequestParam boolean completed) {
        Task task = taskRepository.findById(id).orElseThrow();


        TaskHistory history = new TaskHistory();
        history.setChangeTime(LocalDateTime.now());
        history.setOldState(task.isCompleted() ? "Выполнено" : "В процессе");
        history.setNewState(completed ? "Выполнено" : "В процессе");
        history.setTask(task);
        taskHistoryRepository.save(history);

        task.setCompleted(completed);
        taskRepository.save(task);
        return "redirect:/";
    }


    @PostMapping("/addSubTask/{taskId}")
    public String addSubTask(@PathVariable Long taskId, @RequestParam String description) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        SubTask subTask = new SubTask();
        subTask.setDescription(description);
        subTask.setTask(task);
        subTaskRepository.save(subTask);
        return "redirect:/";
    }
}