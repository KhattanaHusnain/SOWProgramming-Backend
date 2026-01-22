package com.user.management.service;

import com.user.management.dto.AssignmentRequest;
import com.user.management.entity.Assignment;
import com.user.management.entity.Course;
import com.user.management.repository.AssignmentRepository;
import com.user.management.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentService {

    private final AssignmentRepository assignmentRepo;
    private final CourseRepository courseRepo;

    public Assignment create(Long courseId, AssignmentRequest request) {

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        Assignment assignment = Assignment.builder()
                .title(request.title())
                .description(request.description())
                .maxMarks(request.maxMarks())
                .dueDate(request.dueDate())
                .course(course)
                .build();

        return assignmentRepo.save(assignment);
    }
}
