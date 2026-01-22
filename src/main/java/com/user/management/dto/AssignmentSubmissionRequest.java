package com.user.management.dto;

import jakarta.validation.constraints.NotNull;

public record AssignmentSubmissionRequest(

        @NotNull
        Long assignmentId,

        @NotNull
        String submissionUrl
) {}
