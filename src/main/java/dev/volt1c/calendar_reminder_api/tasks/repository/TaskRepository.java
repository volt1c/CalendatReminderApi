package dev.volt1c.calendar_reminder_api.tasks.repository;

import dev.volt1c.calendar_reminder_api.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByDeadlineBetween(Instant deadlineAfter, Instant deadlineBefore);
}