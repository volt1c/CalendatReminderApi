package dev.volt1c.calendar_reminder_api.users.controller;

import dev.volt1c.calendar_reminder_api.users.entity.User;
import dev.volt1c.calendar_reminder_api.users.repository.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/users")
@RestController
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userService) {
        this.userRepository = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/")
    public List<User> allUsers() {
        return userRepository.findAll();
    }
}