package com.khan.quiz.quiz.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuestionDTO {
    private Long id;
    private String questionText;
    private String difficultyLevel;
    private List<QuizOptionDTO> options;
}
