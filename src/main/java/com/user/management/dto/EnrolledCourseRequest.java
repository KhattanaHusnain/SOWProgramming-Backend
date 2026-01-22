package com.user.management.dto;

import jakarta.validation.constraints.NotNull;

public record EnrolledCourseRequest(

        @NotNull
        Long userId,

        @NotNull
        Long courseId
) {}
