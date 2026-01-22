package com.user.management.repository;

import com.user.management.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findByModuleId(Long moduleId);
    List<Topic> findByModuleIdOrderById(Long moduleId);
}
