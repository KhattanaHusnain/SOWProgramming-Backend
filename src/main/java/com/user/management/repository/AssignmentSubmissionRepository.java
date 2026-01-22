package com.user.management.repository;

import com.user.management.entity.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentSubmissionRepository
        extends JpaRepository<AssignmentSubmission, Long> {

    /* ---------------- BASIC LOOKUPS ---------------- */

    List<AssignmentSubmission> findByUserId(Long userId);

    List<AssignmentSubmission> findByAssignmentId(Long assignmentId);

    Optional<AssignmentSubmission> findByUserIdAndAssignmentId(
            Long userId,
            Long assignmentId
    );

    /* ---------------- EXISTENCE CHECKS ---------------- */

    boolean existsByUserIdAndAssignmentId(
            Long userId,
            Long assignmentId
    );
}
