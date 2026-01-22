package com.user.management.service;

import com.user.management.dto.ModuleRequest;
import com.user.management.entity.Course;
import com.user.management.entity.Module;
import com.user.management.repository.CourseRepository;
import com.user.management.repository.ModuleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ModuleService {

    private final ModuleRepository moduleRepo;
    private final CourseRepository courseRepo;

    // CREATE
    public Module create(Long courseId, ModuleRequest request) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        Module module = Module.builder()
                .title(request.title())
                .description(request.description())
                .orderIndex(request.orderIndex())
                .course(course)
                .build();

        return moduleRepo.save(module);
    }

    // GET ALL MODULES BY COURSE
    public List<Module> getAllByCourse(Long courseId) {
        return moduleRepo.findByCourseIdOrderByOrderIndex(courseId);
    }

    // GET MODULE BY ID
    public Module getById(Long courseId, Long moduleId) {
        Module module = moduleRepo.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module not found"));

        if (!module.getCourse().getId().equals(courseId)) {
            throw new IllegalArgumentException("Module does not belong to this course");
        }

        return module;
    }

    // PUT (FULL UPDATE)
    public Module update(Long courseId, Long moduleId, ModuleRequest request) {
        Module module = getById(courseId, moduleId);

        module.setTitle(request.title());
        module.setDescription(request.description());
        module.setOrderIndex(request.orderIndex());

        return moduleRepo.save(module);
    }

    // PATCH (PARTIAL UPDATE)
    public Module patch(Long courseId, Long moduleId, ModuleRequest request) {
        Module module = getById(courseId, moduleId);

        if (request.title() != null)
            module.setTitle(request.title());

        if (request.description() != null)
            module.setDescription(request.description());

        if (request.orderIndex() != null)
            module.setOrderIndex(request.orderIndex());

        return moduleRepo.save(module);
    }

    // DELETE
    public void delete(Long courseId, Long moduleId) {
        Module module = getById(courseId, moduleId);
        moduleRepo.delete(module);
    }
}
