package dev.volt1c.calendar_reminder_api.tasks.repository;

import dev.volt1c.calendar_reminder_api.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByCreatedByAndDeadlineBetween(String createdBy, Instant deadlineAfter, Instant deadlineBefore);
    List<Task> findAllByCreatedBy(String createdBy);
    Optional<Task> findByIdAndCreatedBy(Long id, String createdBy);
}