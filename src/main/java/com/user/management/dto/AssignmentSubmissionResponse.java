package com.user.management.dto;

import java.time.LocalDateTime;

public record AssignmentSubmissionResponse(

        Long id,
        Long userId,
        Long assignmentId,

        String submissionUrl,
        Integer obtainedMarks,
        LocalDateTime submittedAt
) {}
