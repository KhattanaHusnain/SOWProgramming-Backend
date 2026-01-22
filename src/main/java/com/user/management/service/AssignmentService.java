package com.user.management.service;

import com.user.management.dto.AssignmentRequest;
import com.user.management.dto.AssignmentResponse;
import com.user.management.entity.Assignment;
import com.user.management.entity.Course;
import com.user.management.repository.AssignmentRepository;
import com.user.management.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentService {

    private final AssignmentRepository assignmentRepo;
    private final CourseRepository courseRepo;

    /* ---------------- CREATE ---------------- */
    public AssignmentResponse create(Long courseId, AssignmentRequest request) {
        Course course = getCourse(courseId);

        Assignment assignment = Assignment.builder()
                .title(request.title())
                .description(request.description())
                .maxMarks(request.maxMarks())
                .dueDate(request.dueDate())
                .course(course)
                .build();

        return toResponse(assignmentRepo.save(assignment));
    }

    /* ---------------- GET ALL ---------------- */
    public List<AssignmentResponse> getAll(Long courseId) {
        return assignmentRepo.findByCourseId(courseId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /* ---------------- GET ONE ---------------- */
    public AssignmentResponse getOne(Long courseId, Long assignmentId) {
        return toResponse(getAssignment(courseId, assignmentId));
    }

    /* ---------------- FULL UPDATE (PUT) ---------------- */
    public AssignmentResponse update(
            Long courseId,
            Long assignmentId,
            AssignmentRequest request
    ) {
        Assignment a = getAssignment(courseId, assignmentId);

        a.setTitle(request.title());
        a.setDescription(request.description());
        a.setMaxMarks(request.maxMarks());
        a.setDueDate(request.dueDate());

        return toResponse(assignmentRepo.save(a));
    }

    /* ---------------- PARTIAL UPDATE (PATCH) ---------------- */
    public AssignmentResponse patch(
            Long courseId,
            Long assignmentId,
            AssignmentRequest request
    ) {
        Assignment a = getAssignment(courseId, assignmentId);

        if (request.title() != null) a.setTitle(request.title());
        if (request.description() != null) a.setDescription(request.description());
        if (request.maxMarks() != null) a.setMaxMarks(request.maxMarks());
        if (request.dueDate() != null) a.setDueDate(request.dueDate());

        return toResponse(assignmentRepo.save(a));
    }

    /* ---------------- DELETE ---------------- */
    public void delete(Long courseId, Long assignmentId) {
        assignmentRepo.delete(getAssignment(courseId, assignmentId));
    }

    /* ---------------- MAPPER ---------------- */
    private AssignmentResponse toResponse(Assignment a) {
        return new AssignmentResponse(
                a.getId(),
                a.getTitle(),
                a.getDescription(),
                a.getMaxMarks(),
                a.getDueDate(),
                a.getCourse().getId()
        );
    }

    /* ---------------- HELPERS ---------------- */
    private Assignment getAssignment(Long courseId, Long assignmentId) {
        return assignmentRepo.findByIdAndCourseId(assignmentId, courseId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
    }

    private Course getCourse(Long courseId) {
        return courseRepo.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }
}
