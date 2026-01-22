package com.user.management.service;

import com.user.management.dto.QuizRequest;
import com.user.management.entity.Course;
import com.user.management.entity.Quiz;
import com.user.management.repository.CourseRepository;
import com.user.management.repository.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {

    private final QuizRepository quizRepo;
    private final CourseRepository courseRepo;

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
}
