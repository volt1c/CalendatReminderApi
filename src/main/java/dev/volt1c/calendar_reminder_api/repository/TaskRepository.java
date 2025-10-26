package dev.volt1c.calendar_reminder_api.repository;

import dev.volt1c.calendar_reminder_api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}