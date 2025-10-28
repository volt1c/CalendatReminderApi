package dev.volt1c.calendar_reminder_api.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginUserDto {
    private String username;
    private String password;
}
