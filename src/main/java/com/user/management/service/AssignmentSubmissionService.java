package com.user.management.service;

import com.user.management.dto.AssignmentSubmissionRequest;
import com.user.management.dto.AssignmentSubmissionResponse;
import com.user.management.entity.AssignmentSubmission;
import com.user.management.repository.AssignmentRepository;
import com.user.management.repository.AssignmentSubmissionRepository;
import com.user.management.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentSubmissionService {

    private final AssignmentSubmissionRepository repo;
    private final AssignmentRepository assignmentRepo;
    private final UserRepository userRepo;

    /* ---------------- CREATE ---------------- */
    public AssignmentSubmissionResponse submit(
            Long userId,
            Long assignmentId,
            AssignmentSubmissionRequest request
    ) {
        var user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        var assignment = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));

        AssignmentSubmission submission = AssignmentSubmission.builder()
                .user(user)
                .assignment(assignment)
                .submissionUrl(request.submissionUrl())
                .submittedAt(LocalDateTime.now())
                .build();

        return toResponse(repo.save(submission));
    }

    /* ---------------- GET ALL ---------------- */
    public List<AssignmentSubmissionResponse> getAllByUser(Long userId) {
        return repo.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /* ---------------- GET ONE ---------------- */
    public AssignmentSubmissionResponse getOne(Long id) {
        return toResponse(getSubmission(id));
    }

    /* ---------------- PUT ---------------- */
    public AssignmentSubmissionResponse update(
            Long id,
            AssignmentSubmissionRequest request
    ) {
        AssignmentSubmission s = getSubmission(id);
        s.setSubmissionUrl(request.submissionUrl());
        return toResponse(repo.save(s));
    }

    /* ---------------- PATCH ---------------- */
    public AssignmentSubmissionResponse patch(
            Long id,
            AssignmentSubmissionRequest request
    ) {
        AssignmentSubmission s = getSubmission(id);

        if (request.submissionUrl() != null) {
            s.setSubmissionUrl(request.submissionUrl());
        }

        return toResponse(repo.save(s));
    }

    /* ---------------- DELETE ---------------- */
    public void delete(Long id) {
        repo.delete(getSubmission(id));
    }

    /* ---------------- MAPPER ---------------- */
    private AssignmentSubmissionResponse toResponse(AssignmentSubmission s) {
        return new AssignmentSubmissionResponse(
                s.getId(),
                s.getUser().getId(),
                s.getAssignment().getId(),
                s.getSubmissionUrl(),
                s.getObtainedMarks(),
                s.getSubmittedAt()
        );
    }

    private AssignmentSubmission getSubmission(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found"));
    }
}
