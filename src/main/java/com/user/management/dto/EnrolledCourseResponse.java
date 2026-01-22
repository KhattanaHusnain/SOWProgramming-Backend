package com.user.management.dto;

public record EnrolledCourseResponse(
        Long enrollmentId,
        Long courseId,
        String courseTitle,
        Double progressPercentage,
        Boolean completed
) {}
