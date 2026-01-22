package com.user.management.controller;

import com.user.management.dto.QuizRequest;
import com.user.management.dto.QuizResponse;
import com.user.management.entity.Quiz;
import com.user.management.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService service;

    // CREATE
    @PostMapping
    public ResponseEntity<QuizResponse> create(
            @PathVariable Long courseId,
            @RequestBody QuizRequest request
    ) {
        Quiz q = service.create(courseId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(q));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<QuizResponse>> getAll(
            @PathVariable Long courseId
    ) {
        List<QuizResponse> response = service.getAllByCourse(courseId)
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    // GET ONE
    @GetMapping("/{quizId}")
    public ResponseEntity<QuizResponse> getById(
            @PathVariable Long courseId,
            @PathVariable Long quizId
    ) {
        return ResponseEntity.ok(
                toResponse(service.getById(courseId, quizId))
        );
    }

    // PUT (FULL UPDATE)
    @PutMapping("/{quizId}")
    public ResponseEntity<QuizResponse> update(
            @PathVariable Long courseId,
            @PathVariable Long quizId,
            @RequestBody QuizRequest request
    ) {
        return ResponseEntity.ok(
                toResponse(service.update(courseId, quizId, request))
        );
    }

    // PATCH (PARTIAL UPDATE)
    @PatchMapping("/{quizId}")
    public ResponseEntity<QuizResponse> patch(
            @PathVariable Long courseId,
            @PathVariable Long quizId,
            @RequestBody QuizRequest request
    ) {
        return ResponseEntity.ok(
                toResponse(service.patch(courseId, quizId, request))
        );
    }

    // DELETE
    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long courseId,
            @PathVariable Long quizId
    ) {
        service.delete(courseId, quizId);
        return ResponseEntity.noContent().build();
    }

    private QuizResponse toResponse(Quiz q) {
        return new QuizResponse(
                q.getId(),
                q.getTitle(),
                q.getTotalMarks(),
                q.getPassingMarks(),
                q.getTimeLimitMinutes(),
                q.getCourse().getId()
        );
    }
}
