package com.user.management.service;

import com.user.management.entity.CourseProgress;
import com.user.management.repository.CourseProgressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseProgressService {

    private final CourseProgressRepository repo;

    public CourseProgress updateProgress(
            CourseProgress progress,
            double percentage
    ) {
        progress.setProgress(percentage);
        progress.setCompleted(percentage >= 100);
        return repo.save(progress);
    }
}
