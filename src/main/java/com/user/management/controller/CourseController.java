package com.user.management.controller;

import com.user.management.dto.CourseRequest;
import com.user.management.dto.CourseResponse;
import com.user.management.entity.Course;
import com.user.management.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    /* ---------------- CREATE ---------------- */

    @PostMapping
    public ResponseEntity<CourseResponse> create(
            @RequestBody CourseRequest request
    ) {
        Course course = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(course));
    }

    /* ---------------- GET ---------------- */

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(service.getById(id)));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAll() {
        return ResponseEntity.ok(
                service.getAll()
                        .stream()
                        .map(this::toResponse)
                        .toList()
        );
    }

    @GetMapping("/published")
    public ResponseEntity<List<CourseResponse>> getPublished() {
        return ResponseEntity.ok(
                service.getAllPublished()
                        .stream()
                        .map(this::toResponse)
                        .toList()
        );
    }

    /* ---------------- UPDATE ---------------- */

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> update(
            @PathVariable Long id,
            @RequestBody CourseRequest request
    ) {
        return ResponseEntity.ok(
                toResponse(service.update(id, request))
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CourseResponse> updatePartial(
            @PathVariable Long id,
            @RequestBody CourseRequest request
    ) {
        Course updated = service.updatePartial(id, request);
        return ResponseEntity.ok(toResponse(updated));
    }



    /* ---------------- DELETE ---------------- */

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    /* ---------------- Mapper ---------------- */

    private CourseResponse toResponse(Course c) {
        return new CourseResponse(
                c.getId(),
                c.getTitle(),
                c.getShortDescription(),
                c.getThumbnailUrl(),
                c.getLevel().name(),
                c.getDurationInHours(),
                c.getPrice(),
                c.getPublished(),
                c.getCreatedAt()
        );
    }
}
