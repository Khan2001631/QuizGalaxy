package com.khan.quiz.quiz.respository;

import com.khan.quiz.quiz.model.UserQuizSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuizSessionRepository extends JpaRepository<UserQuizSession,Long> {
}
