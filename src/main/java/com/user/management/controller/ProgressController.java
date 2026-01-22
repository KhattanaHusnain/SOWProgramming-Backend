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

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final QuizAttemptService quizAttemptService;
    private final AssignmentSubmissionService assignmentService;

    @PostMapping("/quiz/{userId}/{quizId}")
    public QuizAttemptResponse attemptQuiz(
            @PathVariable Long userId,
            @PathVariable Long quizId,
            @RequestBody QuizAttemptRequest request
    ) {
        QuizAttempt attempt =
                quizAttemptService.attempt(userId, quizId, request.score());

        return new QuizAttemptResponse(
                attempt.getId(),
                userId,
                quizId,
                attempt.getScore(),
                attempt.getPassed(),
                attempt.getAttemptedAt()
        );
    }

    @PostMapping("/assignment/{userId}/{assignmentId}")
    public AssignmentSubmissionResponse submitAssignment(
            @PathVariable Long userId,
            @PathVariable Long assignmentId,
            @RequestBody AssignmentSubmissionRequest request
    ) {
        AssignmentSubmission s =
                assignmentService.submit(
                        userId,
                        assignmentId,
                        request.submissionUrl()
                );

        return new AssignmentSubmissionResponse(
                s.getId(),
                userId,
                assignmentId,
                s.getSubmissionUrl(),
                s.getObtainedMarks(),
                s.getSubmittedAt()
        );
    }
}
