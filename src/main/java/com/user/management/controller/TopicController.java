package com.user.management.controller;

import com.user.management.dto.TopicRequest;
import com.user.management.dto.TopicResponse;
import com.user.management.entity.Topic;
import com.user.management.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules/{moduleId}/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService service;

    // CREATE
    @PostMapping
    public ResponseEntity<TopicResponse> create(
            @PathVariable Long moduleId,
            @RequestBody TopicRequest request
    ) {
        Topic t = service.create(moduleId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(t));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAll(
            @PathVariable Long moduleId
    ) {
        List<TopicResponse> response = service.getAllByModule(moduleId)
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    // GET ONE
    @GetMapping("/{topicId}")
    public ResponseEntity<TopicResponse> getById(
            @PathVariable Long moduleId,
            @PathVariable Long topicId
    ) {
        return ResponseEntity.ok(
                toResponse(service.getById(moduleId, topicId))
        );
    }

    // PUT (FULL UPDATE)
    @PutMapping("/{topicId}")
    public ResponseEntity<TopicResponse> update(
            @PathVariable Long moduleId,
            @PathVariable Long topicId,
            @RequestBody TopicRequest request
    ) {
        return ResponseEntity.ok(
                toResponse(service.update(moduleId, topicId, request))
        );
    }

    // PATCH (PARTIAL UPDATE)
    @PatchMapping("/{topicId}")
    public ResponseEntity<TopicResponse> patch(
            @PathVariable Long moduleId,
            @PathVariable Long topicId,
            @RequestBody TopicRequest request
    ) {
        return ResponseEntity.ok(
                toResponse(service.patch(moduleId, topicId, request))
        );
    }

    // DELETE
    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long moduleId,
            @PathVariable Long topicId
    ) {
        service.delete(moduleId, topicId);
        return ResponseEntity.noContent().build();
    }

    private TopicResponse toResponse(Topic t) {
        return new TopicResponse(
                t.getId(),
                t.getTitle(),
                t.getContent(),
                t.getVideoUrl(),
                t.getDurationInMinutes(),
                t.getModule().getId()
        );
    }
}
