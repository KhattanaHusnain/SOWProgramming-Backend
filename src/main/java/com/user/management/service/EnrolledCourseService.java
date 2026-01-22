package com.user.management.service;

import com.user.management.entity.Course;
import com.user.management.entity.EnrolledCourse;
import com.user.management.entity.User;
import com.user.management.repository.CourseRepository;
import com.user.management.repository.EnrolledCourseRepository;
import com.user.management.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrolledCourseService {

    private final EnrolledCourseRepository enrollmentRepo;
    private final UserRepository userRepo;
    private final CourseRepository courseRepo;

    public EnrolledCourse enroll(Long userId, Long courseId) {

        if (enrollmentRepo.existsByUserIdAndCourseId(userId, courseId)) {
            throw new IllegalArgumentException("User already enrolled");
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        EnrolledCourse enrollment = EnrolledCourse.builder()
                .user(user)
                .course(course)
                .enrolledAt(LocalDateTime.now())
                .progressPercentage(0.0)
                .completed(false)
                .build();

        return enrollmentRepo.save(enrollment);
    }

    public List<EnrolledCourse> getUserEnrollments(Long userId) {
        return enrollmentRepo.findByUserId(userId);
    }
}
