package com.user.management.dto;

import java.time.LocalDateTime;

public record CourseResponse(
        Long id,
        String title,
        String shortDescription,
        String thumbnailUrl,
        String level,
        Integer durationInHours,
        Double price,
        Boolean published,
        LocalDateTime createdAt
) {}
