package dev.volt1c.calendar_reminder_api.tasks.controller;

import dev.volt1c.calendar_reminder_api.tasks.dto.CreateTaskDto;
import dev.volt1c.calendar_reminder_api.tasks.dto.UpdateTaskDto;
import dev.volt1c.calendar_reminder_api.tasks.entity.Task;
import dev.volt1c.calendar_reminder_api.tasks.repository.TaskRepository;

import dev.volt1c.calendar_reminder_api.users.entity.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping
    public Task createTask(@AuthenticationPrincipal User user, @RequestBody CreateTaskDto createTaskDto) {
        Task task = new Task(
                createTaskDto.getName(),
                createTaskDto.getDescription(),
                createTaskDto.getDeadline(),
                user.getUsername());
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
    public ResponseEntity<Task> updateTask(@AuthenticationPrincipal User user, @PathVariable Long id, @RequestBody UpdateTaskDto updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setName(updatedTask.getName());
                    task.setDescription(updatedTask.getDescription());
                    task.setDeadline(updatedTask.getDeadline());
                    task.setUpdatedAt(Instant.now());
                    task.setUpdatedBy(user.getUsername());
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