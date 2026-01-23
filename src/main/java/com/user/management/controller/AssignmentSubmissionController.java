package com.user.management.controller;

import com.user.management.dto.AssignmentSubmissionRequest;
import com.user.management.dto.AssignmentSubmissionResponse;
import com.user.management.service.AssignmentSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress/assignments")
@RequiredArgsConstructor
public class AssignmentSubmissionController {

    private final AssignmentSubmissionService service;

    /* ---------- CREATE ---------- */

    @PostMapping("/{userId}/{assignmentId}")
    public AssignmentSubmissionResponse submitAssignment(
            @PathVariable Long userId,
            @PathVariable Long assignmentId,
            @RequestBody AssignmentSubmissionRequest request
    ) {
        return service.submit(userId, assignmentId, request);
    }

    /* ---------- READ ---------- */

    @GetMapping("/user/{userId}")
    public List<AssignmentSubmissionResponse> getUserSubmissions(
            @PathVariable Long userId
    ) {
        return service.getAllByUser(userId);
    }

    @GetMapping("/{submissionId}")
    public AssignmentSubmissionResponse getSubmission(
            @PathVariable Long submissionId
    ) {
        return service.getOne(submissionId);
    }

    /* ---------- UPDATE ---------- */

    @PutMapping("/{submissionId}")
    public AssignmentSubmissionResponse updateSubmission(
            @PathVariable Long submissionId,
            @RequestBody AssignmentSubmissionRequest request
    ) {
        return service.update(submissionId, request);
    }

    @PatchMapping("/{submissionId}")
    public AssignmentSubmissionResponse patchSubmission(
            @PathVariable Long submissionId,
            @RequestBody AssignmentSubmissionRequest request
    ) {
        return service.patch(submissionId, request);
    }

    /* ---------- DELETE ---------- */

    @DeleteMapping("/{submissionId}")
    public void deleteSubmission(
            @PathVariable Long submissionId
    ) {
        service.delete(submissionId);
    }
}
