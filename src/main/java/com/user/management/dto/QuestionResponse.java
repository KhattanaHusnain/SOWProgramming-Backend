package com.user.management.dto;

public record QuestionResponse(

        Long id,
        String questionText,
        String options,
        Integer marks,

        Long quizId
) {}
