package com.khan.quiz.quiz.respository;

import com.khan.quiz.quiz.model.User;
import com.khan.quiz.quiz.model.UserQuizSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuizSessionRepository extends JpaRepository<UserQuizSession,Long> {
    List<UserQuizSession> findByUserOrderByStartTimeDesc(User user);
    List<UserQuizSession> findByUser(User user);

}
