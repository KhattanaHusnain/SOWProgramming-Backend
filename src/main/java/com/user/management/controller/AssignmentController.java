package com.user.management.controller;

import com.user.management.dto.AssignmentRequest;
import com.user.management.dto.AssignmentResponse;
import com.user.management.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService service;

    @PostMapping
    public ResponseEntity<AssignmentResponse> create(
            @PathVariable Long courseId,
            @RequestBody AssignmentRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(courseId, request));
    }

    @GetMapping
    public ResponseEntity<List<AssignmentResponse>> getAll(
            @PathVariable Long courseId
    ) {
        return ResponseEntity.ok(service.getAll(courseId));
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<AssignmentResponse> getOne(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId
    ) {
        return ResponseEntity.ok(service.getOne(courseId, assignmentId));
    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<AssignmentResponse> update(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @RequestBody AssignmentRequest request
    ) {
        return ResponseEntity.ok(
                service.update(courseId, assignmentId, request)
        );
    }

    @PatchMapping("/{assignmentId}")
    public ResponseEntity<AssignmentResponse> patch(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId,
            @RequestBody AssignmentRequest request
    ) {
        return ResponseEntity.ok(
                service.patch(courseId, assignmentId, request)
        );
    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long courseId,
            @PathVariable Long assignmentId
    ) {
        service.delete(courseId, assignmentId);
        return ResponseEntity.noContent().build();
    }
}
