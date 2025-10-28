package dev.volt1c.calendar_reminder_api.auth.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;

    private long expiresIn;
}