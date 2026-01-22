package com.user.management.service;

import com.user.management.dto.QuizRequest;
import com.user.management.entity.Course;
import com.user.management.entity.Quiz;
import com.user.management.repository.CourseRepository;
import com.user.management.repository.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {

    private final QuizRepository quizRepo;
    private final CourseRepository courseRepo;

    // CREATE
    public Quiz create(Long courseId, QuizRequest request) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        Quiz quiz = Quiz.builder()
                .title(request.title())
                .totalMarks(request.totalMarks())
                .passingMarks(request.passingMarks())
                .timeLimitMinutes(request.timeLimitMinutes())
                .course(course)
                .build();

        return quizRepo.save(quiz);
    }

    // GET ALL QUIZZES BY COURSE
    public List<Quiz> getAllByCourse(Long courseId) {
        return quizRepo.findByCourseIdOrderById(courseId);
    }

    // GET ONE QUIZ
    public Quiz getById(Long courseId, Long quizId) {
        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        if (!quiz.getCourse().getId().equals(courseId)) {
            throw new IllegalArgumentException("Quiz does not belong to this course");
        }

        return quiz;
    }

    // PUT (FULL UPDATE)
    public Quiz update(Long courseId, Long quizId, QuizRequest request) {
        Quiz quiz = getById(courseId, quizId);

        quiz.setTitle(request.title());
        quiz.setTotalMarks(request.totalMarks());
        quiz.setPassingMarks(request.passingMarks());
        quiz.setTimeLimitMinutes(request.timeLimitMinutes());

        return quizRepo.save(quiz);
    }

    // PATCH (PARTIAL UPDATE)
    public Quiz patch(Long courseId, Long quizId, QuizRequest request) {
        Quiz quiz = getById(courseId, quizId);

        if (request.title() != null)
            quiz.setTitle(request.title());

        if (request.totalMarks() != null)
            quiz.setTotalMarks(request.totalMarks());

        if (request.passingMarks() != null)
            quiz.setPassingMarks(request.passingMarks());

        if (request.timeLimitMinutes() != null)
            quiz.setTimeLimitMinutes(request.timeLimitMinutes());

        return quizRepo.save(quiz);
    }

    // DELETE
    public void delete(Long courseId, Long quizId) {
        Quiz quiz = getById(courseId, quizId);
        quizRepo.delete(quiz);
    }
}
