package com.user.management.repository;

import com.user.management.entity.CourseProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseProgressRepository extends JpaRepository<CourseProgress, Long> {
}
