package dev.volt1c.calendar_reminder_api.tasks.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

@Getter
@Setter
@Builder
public class UpdateTaskDto {
    @NotNull
    private long id;

    @NotNull
    @Length(min = 1, max = 100)
    private String name;

    private String description;

    private Instant deadline;

    private boolean isDone;
}
