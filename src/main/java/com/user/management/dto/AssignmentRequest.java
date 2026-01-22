package com.user.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AssignmentRequest(

        @NotBlank
        String title,

        String description,

        @NotNull
        Integer maxMarks,

        LocalDateTime dueDate
) {}
