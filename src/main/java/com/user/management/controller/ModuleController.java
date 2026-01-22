package com.user.management.controller;

import com.user.management.dto.ModuleRequest;
import com.user.management.dto.ModuleResponse;
import com.user.management.entity.Module;
import com.user.management.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService service;

    // CREATE
    @PostMapping
    public ResponseEntity<ModuleResponse> create(
            @PathVariable Long courseId,
            @RequestBody ModuleRequest request
    ) {
        Module m = service.create(courseId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(m));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<ModuleResponse>> getAll(
            @PathVariable Long courseId
    ) {
        List<ModuleResponse> response = service.getAllByCourse(courseId)
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    // GET ONE
    @GetMapping("/{moduleId}")
    public ResponseEntity<ModuleResponse> getById(
            @PathVariable Long courseId,
            @PathVariable Long moduleId
    ) {
        return ResponseEntity.ok(
                toResponse(service.getById(courseId, moduleId))
        );
    }

    // PUT (FULL UPDATE)
    @PutMapping("/{moduleId}")
    public ResponseEntity<ModuleResponse> update(
            @PathVariable Long courseId,
            @PathVariable Long moduleId,
            @RequestBody ModuleRequest request
    ) {
        return ResponseEntity.ok(
                toResponse(service.update(courseId, moduleId, request))
        );
    }

    // PATCH (PARTIAL UPDATE)
    @PatchMapping("/{moduleId}")
    public ResponseEntity<ModuleResponse> patch(
            @PathVariable Long courseId,
            @PathVariable Long moduleId,
            @RequestBody ModuleRequest request
    ) {
        return ResponseEntity.ok(
                toResponse(service.patch(courseId, moduleId, request))
        );
    }

    // DELETE
    @DeleteMapping("/{moduleId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long courseId,
            @PathVariable Long moduleId
    ) {
        service.delete(courseId, moduleId);
        return ResponseEntity.noContent().build();
    }

    private ModuleResponse toResponse(Module m) {
        return new ModuleResponse(
                m.getId(),
                m.getTitle(),
                m.getDescription(),
                m.getOrderIndex(),
                m.getCourse().getId()
        );
    }
}
