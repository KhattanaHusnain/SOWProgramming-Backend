package com.user.management.dto;

public record CourseProgressResponse(

        Long id,
        Long userId,
        Long courseId,

        Double progress,
        Boolean completed
) {}
