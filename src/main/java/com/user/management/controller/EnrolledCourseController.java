package com.user.management.controller;

import com.user.management.dto.EnrolledCourseRequest;
import com.user.management.dto.EnrolledCourseResponse;
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

    /* ---------------- CREATE ---------------- */

    @PostMapping
    public ResponseEntity<EnrolledCourseResponse> enroll(
            @RequestBody EnrolledCourseRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.enroll(
                        request.userId(),
                        request.courseId()
                ));
    }

    /* ---------------- READ ---------------- */

    @GetMapping("/user/{userId}")
    public List<EnrolledCourseResponse> getUserEnrollments(
            @PathVariable Long userId
    ) {
        return service.getUserEnrollments(userId);
    }

    @GetMapping("/{enrollmentId}")
    public EnrolledCourseResponse getEnrollment(
            @PathVariable Long enrollmentId
    ) {
        return service.getOne(enrollmentId);
    }

    /* ---------------- UPDATE ---------------- */

    @PutMapping("/{enrollmentId}")
    public EnrolledCourseResponse updateEnrollment(
            @PathVariable Long enrollmentId,
            @RequestBody EnrolledCourseRequest request
    ) {
        return service.update(enrollmentId, request);
    }

    @PatchMapping("/{enrollmentId}")
    public EnrolledCourseResponse patchEnrollment(
            @PathVariable Long enrollmentId,
            @RequestBody EnrolledCourseRequest request
    ) {
        return service.patch(enrollmentId, request);
    }

    /* ---------------- DELETE ---------------- */

    @DeleteMapping("/{enrollmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnrollment(
            @PathVariable Long enrollmentId
    ) {
        service.delete(enrollmentId);
    }
}
