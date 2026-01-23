package com.user.management.service;

import com.user.management.dto.EnrolledCourseRequest;
import com.user.management.dto.EnrolledCourseResponse;
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

    /* ---------------- CREATE ---------------- */

    public EnrolledCourseResponse enroll(Long userId, Long courseId) {

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

        return toResponse(enrollmentRepo.save(enrollment));
    }

    /* ---------------- READ ---------------- */

    public List<EnrolledCourseResponse> getUserEnrollments(Long userId) {
        return enrollmentRepo.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public EnrolledCourseResponse getOne(Long enrollmentId) {
        return enrollmentRepo.findById(enrollmentId)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
    }

    /* ---------------- UPDATE (PUT) ---------------- */

    public EnrolledCourseResponse update(
            Long enrollmentId,
            EnrolledCourseRequest request
    ) {
        EnrolledCourse enrollment = enrollmentRepo.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));

        enrollment.setProgressPercentage(request.progressPercentage());
        enrollment.setCompleted(request.completed());

        return toResponse(enrollment);
    }

    /* ---------------- PARTIAL UPDATE (PATCH) ---------------- */

    public EnrolledCourseResponse patch(
            Long enrollmentId,
            EnrolledCourseRequest request
    ) {
        EnrolledCourse enrollment = enrollmentRepo.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));

        if (request.progressPercentage() != null) {
            enrollment.setProgressPercentage(request.progressPercentage());
        }

        if (request.completed() != null) {
            enrollment.setCompleted(request.completed());
        }

        return toResponse(enrollment);
    }

    /* ---------------- DELETE ---------------- */

    public void delete(Long enrollmentId) {
        if (!enrollmentRepo.existsById(enrollmentId)) {
            throw new IllegalArgumentException("Enrollment not found");
        }
        enrollmentRepo.deleteById(enrollmentId);
    }

    /* ---------------- MAPPER ---------------- */

    private EnrolledCourseResponse toResponse(EnrolledCourse ec) {
        return new EnrolledCourseResponse(
                ec.getId(),
                ec.getUser().getId(),
                ec.getCourse().getId(),
                ec.getCourse().getTitle(),
                ec.getProgressPercentage(),
                ec.getCompleted(),
                ec.getEnrolledAt(),
                ec.getCompletedAt()
        );
    }
}