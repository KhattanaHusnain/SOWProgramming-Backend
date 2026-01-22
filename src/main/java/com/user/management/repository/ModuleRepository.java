package com.user.management.repository;

import com.user.management.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {

    List<Module> findByCourseId(Long courseId);
    List<Module> findByCourseIdOrderByOrderIndex(Long courseId);

}
