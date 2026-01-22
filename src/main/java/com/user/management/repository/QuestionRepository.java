package com.user.management.repository;

import com.user.management.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByQuizId(Long quizId);
    List<Question> findByQuizIdOrderById(Long quizId);

}
