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

    @PostMapping
    public ResponseEntity<QuizResponse> create(
            @PathVariable Long courseId,
            @RequestBody QuizRequest request
    ) {
        Quiz q = service.create(courseId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new QuizResponse(
                        q.getId(),
                        q.getTitle(),
                        q.getTotalMarks(),
                        q.getPassingMarks(),
                        q.getTimeLimitMinutes(),
                        courseId,
                        List.of()
                ));
    }
}
