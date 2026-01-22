package com.user.management.repository;

import com.user.management.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizAttemptRepository
        extends JpaRepository<QuizAttempt, Long> {

    /* ---------------- BASIC LOOKUPS ---------------- */

    List<QuizAttempt> findByUserId(Long userId);

    List<QuizAttempt> findByQuizId(Long quizId);

    Optional<QuizAttempt> findTopByUserIdAndQuizIdOrderByAttemptedAtDesc(
            Long userId,
            Long quizId
    );

    /* ---------------- VALIDATION ---------------- */

    boolean existsByUserIdAndQuizId(Long userId, Long quizId);
}
