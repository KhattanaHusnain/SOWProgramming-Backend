package com.user.management.dto;

import jakarta.validation.constraints.NotNull;

public record QuizAttemptRequest(

        @NotNull
        Long quizId,

        @NotNull
        Integer score
) {}
