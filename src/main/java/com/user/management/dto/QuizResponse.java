package com.user.management.dto;

import java.util.List;

public record QuizResponse(

        Long id,
        String title,
        Integer totalMarks,
        Integer passingMarks,
        Integer timeLimitMinutes,

        Long courseId,

        List<QuestionResponse> questions
) {}
