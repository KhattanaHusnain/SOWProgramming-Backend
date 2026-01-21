package com.user.management.controller;

import com.user.management.dto.*;
import com.user.management.entity.User;
import com.user.management.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User APIs", description = "CRUD Ops On User")
public class UserController {

    private static final Logger log =
            LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        log.info("Register API called");

        User saved = service.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserResponse(
                        saved.getId(),
                        saved.getUsername(),
                        saved.getEmail()
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        log.info("Login API called");

        User user = service.login(request);

        return ResponseEntity.ok(
                new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        log.info("Get all users API called");

        List<UserResponse> users = service.getAllUsers()
                .stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail()
                ))
                .toList();

        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserRequest request
    ) {
        log.info("Update user API called for userId: {}", id);

        User updated = service.updateUser(id, request);

        return ResponseEntity.ok(
                new UserResponse(
                        updated.getId(),
                        updated.getUsername(),
                        updated.getEmail()
                )
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Delete user API called for userId: {}", id);
        service.deleteUser(id);
    }
}
