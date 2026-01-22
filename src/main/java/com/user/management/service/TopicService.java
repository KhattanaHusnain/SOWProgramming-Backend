package com.user.management.service;

import com.user.management.dto.TopicRequest;
import com.user.management.entity.Module;
import com.user.management.entity.Topic;
import com.user.management.repository.ModuleRepository;
import com.user.management.repository.TopicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TopicService {

    private final TopicRepository topicRepo;
    private final ModuleRepository moduleRepo;

    // CREATE
    public Topic create(Long moduleId, TopicRequest request) {
        Module module = moduleRepo.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module not found"));

        Topic topic = Topic.builder()
                .title(request.title())
                .content(request.content())
                .videoUrl(request.videoUrl())
                .durationInMinutes(request.durationInMinutes())
                .module(module)
                .build();

        return topicRepo.save(topic);
    }

    // GET ALL TOPICS BY MODULE
    public List<Topic> getAllByModule(Long moduleId) {
        return topicRepo.findByModuleIdOrderById(moduleId);
    }

    // GET ONE TOPIC
    public Topic getById(Long moduleId, Long topicId) {
        Topic topic = topicRepo.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found"));

        if (!topic.getModule().getId().equals(moduleId)) {
            throw new IllegalArgumentException("Topic does not belong to this module");
        }

        return topic;
    }

    // PUT (FULL UPDATE)
    public Topic update(Long moduleId, Long topicId, TopicRequest request) {
        Topic topic = getById(moduleId, topicId);

        topic.setTitle(request.title());
        topic.setContent(request.content());
        topic.setVideoUrl(request.videoUrl());
        topic.setDurationInMinutes(request.durationInMinutes());

        return topicRepo.save(topic);
    }

    // PATCH (PARTIAL UPDATE)
    public Topic patch(Long moduleId, Long topicId, TopicRequest request) {
        Topic topic = getById(moduleId, topicId);

        if (request.title() != null)
            topic.setTitle(request.title());

        if (request.content() != null)
            topic.setContent(request.content());

        if (request.videoUrl() != null)
            topic.setVideoUrl(request.videoUrl());

        if (request.durationInMinutes() != null)
            topic.setDurationInMinutes(request.durationInMinutes());

        return topicRepo.save(topic);
    }

    // DELETE
    public void delete(Long moduleId, Long topicId) {
        Topic topic = getById(moduleId, topicId);
        topicRepo.delete(topic);
    }
}
