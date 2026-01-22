package com.user.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuizRequest(

        @NotBlank
        String title,

        @NotNull
        Integer totalMarks,

        @NotNull
        Integer passingMarks,

        Integer timeLimitMinutes
) {}
