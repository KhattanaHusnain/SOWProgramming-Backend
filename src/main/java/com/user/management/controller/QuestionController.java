package com.user.management.controller;

import com.user.management.dto.QuestionRequest;
import com.user.management.dto.QuestionResponse;
import com.user.management.entity.Question;
import com.user.management.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes/{quizId}/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService service;

    // CREATE
    @PostMapping
    public ResponseEntity<QuestionResponse> create(
            @PathVariable Long quizId,
            @RequestBody QuestionRequest request
    ) {
        Question q = service.addQuestion(quizId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(q));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAll(
            @PathVariable Long quizId
    ) {
        List<QuestionResponse> response = service.getAllByQuiz(quizId)
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    // GET ONE
    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponse> getById(
            @PathVariable Long quizId,
            @PathVariable Long questionId
    ) {
        return ResponseEntity.ok(
                toResponse(service.getById(quizId, questionId))
        );
    }

    // PUT (FULL UPDATE)
    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionResponse> update(
            @PathVariable Long quizId,
            @PathVariable Long questionId,
            @RequestBody QuestionRequest request
    ) {
        return ResponseEntity.ok(
                toResponse(service.update(quizId, questionId, request))
        );
    }

    // PATCH (PARTIAL UPDATE)
    @PatchMapping("/{questionId}")
    public ResponseEntity<QuestionResponse> patch(
            @PathVariable Long quizId,
            @PathVariable Long questionId,
            @RequestBody QuestionRequest request
    ) {
        return ResponseEntity.ok(
                toResponse(service.patch(quizId, questionId, request))
        );
    }

    // DELETE
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long quizId,
            @PathVariable Long questionId
    ) {
        service.delete(quizId, questionId);
        return ResponseEntity.noContent().build();
    }

    private QuestionResponse toResponse(Question q) {
        return new QuestionResponse(
                q.getId(),
                q.getQuestionText(),
                q.getOptions(),
                q.getMarks(),
                q.getQuiz().getId()
        );
    }
}
