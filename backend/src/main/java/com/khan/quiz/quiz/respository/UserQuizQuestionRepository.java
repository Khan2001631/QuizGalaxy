package com.khan.quiz.quiz.respository;

import com.khan.quiz.quiz.model.Question;
import com.khan.quiz.quiz.model.UserQuizQuestion;
import com.khan.quiz.quiz.model.UserQuizSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserQuizQuestionRepository extends JpaRepository<UserQuizQuestion, Long> {
    List<UserQuizQuestion> findBySession_IdOrderByQuestionOrderAsc(Long sessionId);

    Optional<UserQuizQuestion> findBySessionAndQuestion(UserQuizSession session, Question question);
}
