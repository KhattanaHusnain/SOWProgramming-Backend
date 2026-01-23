package com.user.management.controller;

import com.user.management.dto.QuizAttemptRequest;
import com.user.management.dto.QuizAttemptResponse;
import com.user.management.service.QuizAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress/quizzes")
@RequiredArgsConstructor
public class QuizAttemptController {

    private final QuizAttemptService service;

    /* ---------- CREATE ---------- */

    @PostMapping("/{userId}/{quizId}")
    public QuizAttemptResponse attemptQuiz(
            @PathVariable Long userId,
            @PathVariable Long quizId,
            @RequestBody QuizAttemptRequest request
    ) {
        return service.attempt(userId, quizId, request);
    }

    /* ---------- READ ---------- */

    @GetMapping("/user/{userId}")
    public List<QuizAttemptResponse> getUserAttempts(
            @PathVariable Long userId
    ) {
        return service.getAllByUser(userId);
    }

    @GetMapping("/{attemptId}")
    public QuizAttemptResponse getAttempt(
            @PathVariable Long attemptId
    ) {
        return service.getOne(attemptId);
    }

    /* ---------- UPDATE ---------- */

    @PutMapping("/{attemptId}")
    public QuizAttemptResponse updateAttempt(
            @PathVariable Long attemptId,
            @RequestBody QuizAttemptRequest request
    ) {
        return service.update(attemptId, request);
    }

    @PatchMapping("/{attemptId}")
    public QuizAttemptResponse patchAttempt(
            @PathVariable Long attemptId,
            @RequestBody QuizAttemptRequest request
    ) {
        return service.patch(attemptId, request);
    }

    /* ---------- DELETE ---------- */

    @DeleteMapping("/{attemptId}")
    public void deleteAttempt(
            @PathVariable Long attemptId
    ) {
        service.delete(attemptId);
    }
}
