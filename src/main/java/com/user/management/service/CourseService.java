package com.user.management.service;

import com.user.management.dto.CourseRequest;
import com.user.management.entity.Course;
import com.user.management.entity.CourseLevel;
import com.user.management.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    public Course create(CourseRequest request) {

        Course course = Course.builder()
                .title(request.title())
                .shortDescription(request.shortDescription())
                .description(request.description())
                .thumbnailUrl(request.thumbnailUrl())
                .level(CourseLevel.valueOf(request.level()))
                .durationInHours(request.durationInHours())
                .price(request.price())
                .language(request.language())
                .published(false)
                .createdAt(LocalDateTime.now())
                .build();

        return courseRepository.save(course);
    }

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    public Course update(Long id, CourseRequest request) {
        Course course = getById(id);

        course.setTitle(request.title());
        course.setShortDescription(request.shortDescription());
        course.setDescription(request.description());
        course.setThumbnailUrl(request.thumbnailUrl());
        course.setLevel(CourseLevel.valueOf(request.level()));
        course.setDurationInHours(request.durationInHours());
        course.setPrice(request.price());
        course.setLanguage(request.language());
        course.setUpdatedAt(LocalDateTime.now());

        return course;
    }

    // existing fields + methods remain

    public Course updatePartial(Long id, CourseRequest request) {

        Course course = getById(id);

        if (request.title() != null)
            course.setTitle(request.title());

        if (request.shortDescription() != null)
            course.setShortDescription(request.shortDescription());

        if (request.description() != null)
            course.setDescription(request.description());

        if (request.thumbnailUrl() != null)
            course.setThumbnailUrl(request.thumbnailUrl());

        if (request.level() != null)
            course.setLevel(CourseLevel.valueOf(request.level()));

        if (request.durationInHours() != null)
            course.setDurationInHours(request.durationInHours());

        if (request.price() != null)
            course.setPrice(request.price());

        if (request.language() != null)
            course.setLanguage(request.language());

        if (request.published() != null)
            course.setPublished(request.published());

        course.setUpdatedAt(LocalDateTime.now());

        return course;
    }

    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new IllegalArgumentException("Course not found");
        }
        courseRepository.deleteById(id);
    }

    public Course getById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }

    public List<Course> getAllPublished() {
        return courseRepository.findByPublishedTrue();
    }

}
