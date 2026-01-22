package com.user.management.service;

import com.user.management.entity.QuizAttempt;
import com.user.management.repository.QuizAttemptRepository;
import com.user.management.repository.QuizRepository;
import com.user.management.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizAttemptService {

    private final QuizAttemptRepository repo;
    private final QuizRepository quizRepo;
    private final UserRepository userRepo;

    public QuizAttempt attempt(Long userId, Long quizId, int score) {

        var quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        var user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean passed = score >= quiz.getPassingMarks();

        QuizAttempt attempt = QuizAttempt.builder()
                .user(user)
                .quiz(quiz)
                .score(score)
                .passed(passed)
                .attemptedAt(java.time.LocalDateTime.now())
                .build();

        return repo.save(attempt);
    }
}
