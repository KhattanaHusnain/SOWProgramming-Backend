package com.user.management.service;

import com.user.management.dto.QuizAttemptRequest;
import com.user.management.dto.QuizAttemptResponse;
import com.user.management.entity.QuizAttempt;
import com.user.management.repository.QuizAttemptRepository;
import com.user.management.repository.QuizRepository;
import com.user.management.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizAttemptService {

    private final QuizAttemptRepository repo;
    private final QuizRepository quizRepo;
    private final UserRepository userRepo;

    /* ---------------- CREATE ---------------- */
    public QuizAttemptResponse attempt(
            Long userId,
            Long quizId,
            QuizAttemptRequest request
    ) {
        var quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        var user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean passed = request.score() >= quiz.getPassingMarks();

        QuizAttempt attempt = QuizAttempt.builder()
                .user(user)
                .quiz(quiz)
                .score(request.score())
                .passed(passed)
                .attemptedAt(LocalDateTime.now())
                .build();

        return toResponse(repo.save(attempt));
    }

    /* ---------------- GET ALL ---------------- */
    public List<QuizAttemptResponse> getAllByUser(Long userId) {
        return repo.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /* ---------------- GET ONE ---------------- */
    public QuizAttemptResponse getOne(Long id) {
        return toResponse(getAttempt(id));
    }

    /* ---------------- PUT ---------------- */
    public QuizAttemptResponse update(Long id, QuizAttemptRequest request) {
        QuizAttempt a = getAttempt(id);

        a.setScore(request.score());
        a.setPassed(request.score() >= a.getQuiz().getPassingMarks());

        return toResponse(repo.save(a));
    }

    /* ---------------- PATCH ---------------- */
    public QuizAttemptResponse patch(Long id, QuizAttemptRequest request) {
        QuizAttempt a = getAttempt(id);

        if (request.score() != null) {
            a.setScore(request.score());
            a.setPassed(request.score() >= a.getQuiz().getPassingMarks());
        }

        return toResponse(repo.save(a));
    }

    /* ---------------- DELETE ---------------- */
    public void delete(Long id) {
        repo.delete(getAttempt(id));
    }

    /* ---------------- MAPPER ---------------- */
    private QuizAttemptResponse toResponse(QuizAttempt a) {
        return new QuizAttemptResponse(
                a.getId(),
                a.getUser().getId(),
                a.getQuiz().getId(),
                a.getScore(),
                a.getPassed(),
                a.getAttemptedAt()
        );
    }

    private QuizAttempt getAttempt(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quiz attempt not found"));
    }
}
