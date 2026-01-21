package com.user.management.dto;

public record ProfileResponse(
        Long id,
        String firstName,
        String lastName,
        String phone,
        Long userId,
        String username,
        String email
) {}
