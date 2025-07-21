package com.khan.quiz.quiz.respository;

import com.khan.quiz.quiz.model.DifficultyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DifficultyLevelRepository extends JpaRepository<DifficultyLevel,Long> {
}
