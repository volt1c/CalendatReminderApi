package dev.volt1c.calendar_reminder_api.tasks.controller;

import dev.volt1c.calendar_reminder_api.tasks.dto.CreateTask;
import dev.volt1c.calendar_reminder_api.tasks.dto.UpdateTask;
import dev.volt1c.calendar_reminder_api.tasks.entity.Task;
import dev.volt1c.calendar_reminder_api.tasks.repository.TaskRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping
    public Task createTask(@RequestBody CreateTask createTask) {
        Task task = new Task(
                createTask.getName(),
                createTask.getDescription(),
                createTask.getDeadline(),
                "user"); // TODO: pobierać z autentykacji
        return taskRepository.save(task);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody UpdateTask updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setName(updatedTask.getName());
                    task.setDescription(updatedTask.getDescription());
                    task.setDeadline(updatedTask.getDeadline());
                    task.setUpdatedAt(Instant.now());
                    task.setUpdatedBy("user"); // TODO: pobierać z autentykacji
                    taskRepository.save(task);
                    return ResponseEntity.ok(task);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/today")
    public List<Task> getTodayTasks() {
        ZoneId zone = ZoneId.systemDefault();
        LocalDate today = LocalDate.now(zone);
        Instant todayStart = today.atStartOfDay(zone).toInstant();
        Instant todayEnd = today.plusDays(1).atStartOfDay(zone).toInstant();
        return taskRepository.findAllByDeadlineBetween(todayStart, todayEnd);
    }
}