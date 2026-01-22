package com.user.management.service;

import com.user.management.entity.AssignmentSubmission;
import com.user.management.repository.AssignmentRepository;
import com.user.management.repository.AssignmentSubmissionRepository;
import com.user.management.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentSubmissionService {

    private final AssignmentSubmissionRepository repo;
    private final AssignmentRepository assignmentRepo;
    private final UserRepository userRepo;

    public AssignmentSubmission submit(
            Long userId,
            Long assignmentId,
            String url
    ) {
        var user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        var assignment = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));

        AssignmentSubmission submission = AssignmentSubmission.builder()
                .user(user)
                .assignment(assignment)
                .submissionUrl(url)
                .submittedAt(java.time.LocalDateTime.now())
                .build();

        return repo.save(submission);
    }
}
