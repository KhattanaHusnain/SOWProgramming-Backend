package com.user.management.dto;

import java.time.LocalDateTime;

public record AssignmentResponse(

        Long id,
        String title,
        String description,
        Integer maxMarks,
        LocalDateTime dueDate,

        Long courseId
) {}
