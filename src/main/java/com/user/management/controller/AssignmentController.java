package com.user.management.controller;

import com.user.management.dto.AssignmentRequest;
import com.user.management.dto.AssignmentResponse;
import com.user.management.entity.Assignment;
import com.user.management.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Assignment a = service.create(courseId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AssignmentResponse(
                        a.getId(),
                        a.getTitle(),
                        a.getDescription(),
                        a.getMaxMarks(),
                        a.getDueDate(),
                        courseId
                ));
    }
}
