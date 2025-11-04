package com.khan.quiz.quiz.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserQuizQuestionDTO {
    private Long questionId;
    private String questionText;
    private String difficultyLevel;
    private Integer questionOrder;
    private boolean answered;
    private boolean skipped;
    private List<QuizOptionDTO> options;
}

