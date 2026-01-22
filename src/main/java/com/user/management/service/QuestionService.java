package com.user.management.service;

import com.user.management.dto.QuestionRequest;
import com.user.management.entity.Question;
import com.user.management.entity.Quiz;
import com.user.management.repository.QuestionRepository;
import com.user.management.repository.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepo;
    private final QuizRepository quizRepo;

    // CREATE
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

    // GET ALL QUESTIONS BY QUIZ
    public List<Question> getAllByQuiz(Long quizId) {
        return questionRepo.findByQuizIdOrderById(quizId);
    }

    // GET ONE QUESTION
    public Question getById(Long quizId, Long questionId) {
        Question question = questionRepo.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        if (!question.getQuiz().getId().equals(quizId)) {
            throw new IllegalArgumentException("Question does not belong to this quiz");
        }

        return question;
    }

    // PUT (FULL UPDATE)
    public Question update(Long quizId, Long questionId, QuestionRequest request) {
        Question question = getById(quizId, questionId);

        question.setQuestionText(request.questionText());
        question.setOptions(request.options());
        question.setCorrectAnswer(request.correctAnswer());
        question.setMarks(request.marks());

        return questionRepo.save(question);
    }

    // PATCH (PARTIAL UPDATE)
    public Question patch(Long quizId, Long questionId, QuestionRequest request) {
        Question question = getById(quizId, questionId);

        if (request.questionText() != null)
            question.setQuestionText(request.questionText());

        if (request.options() != null)
            question.setOptions(request.options());

        if (request.correctAnswer() != null)
            question.setCorrectAnswer(request.correctAnswer());

        if (request.marks() != null)
            question.setMarks(request.marks());

        return questionRepo.save(question);
    }

    // DELETE
    public void delete(Long quizId, Long questionId) {
        Question question = getById(quizId, questionId);
        questionRepo.delete(question);
    }
}
