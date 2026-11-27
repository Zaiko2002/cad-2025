package com.example.todoclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;

public class TaskManagerForm extends JFrame {
    private String authHeader;
    private String username;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<TaskDTO> currentTasks;
    private final String API_URL = "http://localhost:8080/api/tasks";
    private HttpClient client;

    public TaskManagerForm(String authHeader, String username) {
        this.authHeader = authHeader;
        this.username = username;
        this.client = HttpClient.newHttpClient();

        setTitle("ToDo Manager - Пользователь: " + username);
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Таблица ---
        String[] columns = {"ID", "Название", "Категория", "Приоритет", "Статус", "Дата"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Панель управления (снизу) ---
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        // 1. Панель кнопок
        JPanel buttonPanel = new JPanel();
        JButton refreshBtn = new JButton("Обновить");
        JButton addBtn = new JButton("Добавить задачу");
        JButton deleteBtn = new JButton("Удалить (только MODERATOR)");
        JButton sortBtn = new JButton("Сортировать по приоритету");

        buttonPanel.add(refreshBtn);
        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(sortBtn);
        bottomPanel.add(buttonPanel);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Обработчики событий ---
        refreshBtn.addActionListener(e -> loadTasks());

        deleteBtn.addActionListener(e -> deleteTask());

        sortBtn.addActionListener(e -> sortTasksByPriority());

        addBtn.addActionListener(e -> showAddTaskDialog());

        // Загружаем данные при старте
        loadTasks();
    }

    private void loadTasks() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Basic " + authHeader)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                currentTasks = gson.fromJson(response.body(), new TypeToken<List<TaskDTO>>(){}.getType());
                updateTable(currentTasks);
            } else {
                JOptionPane.showMessageDialog(this, "Ошибка загрузки: " + response.statusCode());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ошибка подключения к серверу:\n" + ex.getMessage());
        }
    }

    private void updateTable(List<TaskDTO> tasks) {
        tableModel.setRowCount(0); // Очищаем
        for (TaskDTO t : tasks) {
            tableModel.addRow(new Object[]{
                    t.id, t.title, t.category, t.priority,
                    t.completed ? "Выполнено" : "В процессе",
                    t.createdAt != null ? t.createdAt.replace("T", " ").substring(0, 16) : "-"
            });
        }
    }

    private void deleteTask() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите задачу для удаления!");
            return;
        }

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "/" + id))
                    .header("Authorization", "Basic " + authHeader)
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JOptionPane.showMessageDialog(this, "Задача удалена!");
                loadTasks();
            } else if (response.statusCode() == 403) {
                JOptionPane.showMessageDialog(this, "Отказано в доступе! Вы не модератор.");
            } else {
                JOptionPane.showMessageDialog(this, "Ошибка удаления: " + response.statusCode());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ошибка: " + ex.getMessage());
        }
    }

    private void sortTasksByPriority() {
        if (currentTasks == null) return;

        // Сортируем: Высокий -> Средний -> Низкий
        currentTasks.sort(Comparator.comparingInt(t -> {
            if ("Высокий".equals(t.priority)) return 1;
            if ("Средний".equals(t.priority)) return 2;
            return 3;
        }));
        updateTable(currentTasks);
    }

    private void showAddTaskDialog() {
        JTextField titleField = new JTextField();
        JTextField descField = new JTextField();
        String[] categories = {"Учеба", "Работа", "Личные дела"};
        JComboBox<String> categoryBox = new JComboBox<>(categories);
        String[] priorities = {"Высокий", "Средний", "Низкий"};
        JComboBox<String> priorityBox = new JComboBox<>(priorities);

        Object[] message = {
                "Название:", titleField,
                "Описание:", descField,
                "Категория:", categoryBox,
                "Приоритет:", priorityBox
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Добавить задачу", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                // Формируем JSON вручную
                String jsonBody = String.format(
                        "{\"title\":\"%s\",\"description\":\"%s\",\"category\":\"%s\",\"priority\":\"%s\",\"completed\":false}",
                        titleField.getText(), descField.getText(), categoryBox.getSelectedItem(), priorityBox.getSelectedItem()
                );

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL))
                        .header("Authorization", "Basic " + authHeader)
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    JOptionPane.showMessageDialog(this, "Задача добавлена!");
                    loadTasks();
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка добавления: " + response.statusCode());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка: " + ex.getMessage());
            }
        }
    }

    // Вспомогательный класс для парсинга JSON (Gson)
    static class TaskDTO {
        Long id;
        String title;
        String description;
        String category;
        String priority;
        boolean completed;
        String createdAt;
    }
}