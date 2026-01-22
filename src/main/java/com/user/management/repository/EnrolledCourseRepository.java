package com.user.management.repository;

import com.user.management.entity.EnrolledCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrolledCourseRepository extends JpaRepository<EnrolledCourse, Long> {

    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    List<EnrolledCourse> findByUserId(Long userId);
}
