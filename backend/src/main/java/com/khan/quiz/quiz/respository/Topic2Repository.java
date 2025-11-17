package com.khan.quiz.quiz.respository;

import com.khan.quiz.quiz.model.Topic2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Topic2Repository extends JpaRepository<Topic2, Integer> {
    Topic2 findByTopicName(String name);
}
