package com.khan.quiz.quiz.respository;

import com.khan.quiz.quiz.model.Topic;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long> {
    boolean existsByNameIgnoreCase(@NotBlank(message = "Topic name is required") @Size(max = 100, message = "Topic name must not exceed 100 characters") String name);
}
