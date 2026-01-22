package com.user.management.dto;

import java.time.LocalDateTime;

public record QuizAttemptResponse(

        Long id,
        Long userId,
        Long quizId,

        Integer score,
        Boolean passed,
        LocalDateTime attemptedAt
) {}
