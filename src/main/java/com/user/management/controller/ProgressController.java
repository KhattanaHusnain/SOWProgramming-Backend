package com.user.management.controller;

import com.user.management.dto.AssignmentSubmissionRequest;
import com.user.management.dto.AssignmentSubmissionResponse;
import com.user.management.dto.QuizAttemptRequest;
import com.user.management.dto.QuizAttemptResponse;
import com.user.management.entity.AssignmentSubmission;
import com.user.management.entity.QuizAttempt;
import com.user.management.service.AssignmentSubmissionService;
import com.user.management.service.QuizAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress/quizzes")
@RequiredArgsConstructor
public class ProgressController {

    private final QuizAttemptService quizAttemptService;
    private final AssignmentSubmissionService assignmentService;

    /* ---------- QUIZ ---------- */

    @PostMapping("/{userId}/{quizId}")
    public QuizAttemptResponse attemptQuiz(
            @PathVariable Long userId,
            @PathVariable Long quizId,
            @RequestBody QuizAttemptRequest request
    ) {
        return quizAttemptService.attempt(userId, quizId, request);
    }

    @GetMapping("/user/{userId}")
    public List<QuizAttemptResponse> getUserAttempts(
            @PathVariable Long userId
    ) {
        return quizAttemptService.getAllByUser(userId);
    }

    @GetMapping("/{attemptId}")
    public QuizAttemptResponse getAttempt(
            @PathVariable Long attemptId
    ) {
        return quizAttemptService.getOne(attemptId);
    }

    @PutMapping("/{attemptId}")
    public QuizAttemptResponse updateAttempt(
            @PathVariable Long attemptId,
            @RequestBody QuizAttemptRequest request
    ) {
        return quizAttemptService.update(attemptId, request);
    }

    @PatchMapping("/{attemptId}")
    public QuizAttemptResponse patchAttempt(
            @PathVariable Long attemptId,
            @RequestBody QuizAttemptRequest request
    ) {
        return quizAttemptService.patch(attemptId, request);
    }

    @DeleteMapping("/{attemptId}")
    public void deleteAttempt(
            @PathVariable Long attemptId
    ) {
        quizAttemptService.delete(attemptId);
    }
    /* ---------- ASSIGNMENT ---------- */

    @PostMapping("/assignments/{userId}/{assignmentId}")
    public AssignmentSubmissionResponse submitAssignment(
            @PathVariable Long userId,
            @PathVariable Long assignmentId,
            @RequestBody AssignmentSubmissionRequest request
    ) {
        return assignmentService.submit(userId, assignmentId, request);
    }

    @GetMapping("/assignments/user/{userId}")
    public List<AssignmentSubmissionResponse> getUserSubmissions(
            @PathVariable Long userId
    ) {
        return assignmentService.getAllByUser(userId);
    }

    @GetMapping("/assignments/{submissionId}")
    public AssignmentSubmissionResponse getSubmission(
            @PathVariable Long submissionId
    ) {
        return assignmentService.getOne(submissionId);
    }

    @PutMapping("/assignments/{submissionId}")
    public AssignmentSubmissionResponse updateSubmission(
            @PathVariable Long submissionId,
            @RequestBody AssignmentSubmissionRequest request
    ) {
        return assignmentService.update(submissionId, request);
    }

    @PatchMapping("/assignments/{submissionId}")
    public AssignmentSubmissionResponse patchSubmission(
            @PathVariable Long submissionId,
            @RequestBody AssignmentSubmissionRequest request
    ) {
        return assignmentService.patch(submissionId, request);
    }

    @DeleteMapping("/assignments/{submissionId}")
    public void deleteSubmission(
            @PathVariable Long submissionId
    ) {
        assignmentService.delete(submissionId);
    }
}
