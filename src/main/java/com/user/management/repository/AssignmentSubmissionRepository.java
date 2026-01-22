package com.user.management.repository;

import com.user.management.entity.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentSubmissionRepository
        extends JpaRepository<AssignmentSubmission, Long> {
}

