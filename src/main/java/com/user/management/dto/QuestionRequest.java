package com.user.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestionRequest(

        @NotBlank
        String questionText,

        @NotBlank
        String options,        // JSON or delimited string

        @NotBlank
        String correctAnswer,

        @NotNull
        Integer marks
) {}
