package com.user.management.dto;

import java.time.LocalDateTime;

public record EnrolledCourseResponse(

        Long enrollmentId,

        Long userId,
        Long courseId,

        String courseTitle,

        Double progressPercentage,
        Boolean completed,

        LocalDateTime enrolledAt,
        LocalDateTime completedAt
) {
}
