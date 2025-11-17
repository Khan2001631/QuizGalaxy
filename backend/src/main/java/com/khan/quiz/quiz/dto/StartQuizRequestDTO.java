package com.khan.quiz.quiz.dto;

import com.khan.quiz.quiz.enums.Difficulty;
import lombok.Data;
import java.util.List;

@Data
public class StartQuizRequestDTO {
    private List<Long> topicIds;
    private List<Difficulty> difficultyLevels; // e.g., ["EASY", "HARD"]
    private int totalQuestions;
//    private int durationInSeconds; // We will handle this in backend
    private boolean allowSkipping;
}
