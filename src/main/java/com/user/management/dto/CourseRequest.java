package com.user.management.dto;

public record CourseRequest(
        String title,
        String shortDescription,
        String description,
        String thumbnailUrl,
        String level,
        Integer durationInHours,
        Double price,
        String language,
        Boolean published
) {}
