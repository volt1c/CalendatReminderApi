package dev.volt1c.calendar_reminder_api.tasks.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

@Builder
@Getter
@Setter
public class CreateTask {
    @NotNull
    @Length(min = 1, max = 100)
    private String name;

    private String description;

    @NotNull
    private Instant deadline;
}
