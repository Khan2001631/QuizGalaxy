package com.khan.quiz.quiz.respository;

import com.khan.quiz.quiz.enums.Difficulty;
import com.khan.quiz.quiz.model.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findByTopicId(Long topicId);

    @Query("SELECT q FROM Question q WHERE q.topic.id IN :topicIds AND q.difficulty.level IN :levels ORDER BY FUNCTION('RAND')")
    List<Question> findRandomQuestions(@Param("topicIds") List<Long> topicIds,
                                       @Param("levels") List<Difficulty> levels,
                                       Pageable pageable);

}
