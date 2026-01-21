package com.user.management.dto;

public record UserResponse(
        Long id,
        String username,
        String email
) {}
