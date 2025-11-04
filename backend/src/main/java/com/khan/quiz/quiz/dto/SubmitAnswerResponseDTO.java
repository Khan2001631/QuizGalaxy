package com.khan.quiz.quiz.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmitAnswerResponseDTO {
    private boolean isCorrect;
    private int updatedScore;
    private int attempted;
    private int remainingQuestions;
}

