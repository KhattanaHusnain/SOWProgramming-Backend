package com.user.management.controller;

import com.user.management.dto.QuestionRequest;
import com.user.management.dto.QuestionResponse;
import com.user.management.entity.Question;
import com.user.management.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes/{quizId}/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService service;

    @PostMapping
    public ResponseEntity<QuestionResponse> create(
            @PathVariable Long quizId,
            @RequestBody QuestionRequest request
    ) {
        Question q = service.addQuestion(quizId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new QuestionResponse(
                        q.getId(),
                        q.getQuestionText(),
                        q.getOptions(),
                        q.getMarks(),
                        quizId
                ));
    }
}
