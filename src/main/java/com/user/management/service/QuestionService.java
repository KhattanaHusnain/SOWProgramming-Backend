package com.user.management.service;

import com.user.management.dto.QuestionRequest;
import com.user.management.entity.Question;
import com.user.management.entity.Quiz;
import com.user.management.repository.QuestionRepository;
import com.user.management.repository.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepo;
    private final QuizRepository quizRepo;

    public Question addQuestion(Long quizId, QuestionRequest request) {

        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        Question question = Question.builder()
                .questionText(request.questionText())
                .options(request.options())
                .correctAnswer(request.correctAnswer())
                .marks(request.marks())
                .quiz(quiz)
                .build();

        return questionRepo.save(question);
    }
}
