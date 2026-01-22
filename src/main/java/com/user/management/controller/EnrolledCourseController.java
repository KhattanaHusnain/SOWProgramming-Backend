package com.user.management.controller;

import com.user.management.dto.EnrolledCourseRequest;
import com.user.management.dto.EnrolledCourseResponse;
import com.user.management.entity.EnrolledCourse;
import com.user.management.service.EnrolledCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrolledCourseController {

    private final EnrolledCourseService service;

    @PostMapping
    public ResponseEntity<EnrolledCourseResponse> enroll(
            @RequestBody EnrolledCourseRequest request
    ) {
        EnrolledCourse e = service.enroll(
                request.userId(),
                request.courseId()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new EnrolledCourseResponse(
                        e.getId(),
                        e.getCourse().getId(),
                        e.getCourse().getTitle(),
                        e.getProgressPercentage(),
                        e.getCompleted()
                ));
    }

    @GetMapping("/user/{userId}")
    public List<EnrolledCourseResponse> getUserEnrollments(
            @PathVariable Long userId
    ) {
        return service.getUserEnrollments(userId)
                .stream()
                .map(e -> new EnrolledCourseResponse(
                        e.getId(),
                        e.getCourse().getId(),
                        e.getCourse().getTitle(),
                        e.getProgressPercentage(),
                        e.getCompleted()
                ))
                .toList();
    }
}

