package com.user.management.service;

import com.user.management.dto.LoginRequest;
import com.user.management.dto.RegisterRequest;
import com.user.management.dto.UpdateUserRequest;
import com.user.management.entity.User;
import com.user.management.exception.AuthenticationException;
import com.user.management.exception.ConflictException;
import com.user.management.exception.ResourceNotFoundException;
import com.user.management.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger log =
            LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    /* ------------------ Register ------------------ */

    public User register(RegisterRequest request) {

        if (repository.existsByEmail(request.email())) {
            log.warn("Email already in use: {}", request.email());
            throw new ConflictException("Email already in use");
        }

        if (repository.existsByUsername(request.username())) {
            log.warn("Username already in use: {}", request.username());
            throw new ConflictException("Username already in use");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        User saved = repository.save(user);

        log.info("User registered successfully with id: {}", saved.getId());
        return saved;
    }

    /* ------------------ Login ------------------ */

    public User login(LoginRequest request) {

        User user = repository.findByEmail(request.email())
                .orElseThrow(() -> {
                    log.warn("Login failed: email not found {}", request.email());
                    return new AuthenticationException("Invalid credentials");
                });

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            log.warn("Login failed: wrong password for {}", request.email());
            throw new AuthenticationException("Invalid credentials");
        }

        log.info("User logged in successfully with id: {}", user.getId());
        return user;
    }

    /* ------------------ Get All Users ------------------ */

    public List<User> getAllUsers() {
        log.debug("Fetching all users");
        return repository.findAll();
    }

    /* ------------------ Update User ------------------ */

    public User updateUser(Long id, UpdateUserRequest request) {

        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));

        if (
                repository.existsByUsername(request.username())
                        && !user.getUsername().equals(request.username())
        ) {
            throw new ConflictException("Username already in use");
        }

        user.setUsername(request.username());

        log.info("User updated successfully with id: {}", id);
        return repository.save(user);
    }

    /* ------------------ Delete User ------------------ */

    @Transactional
    public void deleteUser(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));

        repository.delete(user);
        log.info("User deleted successfully with id: {}", id);
    }
}
