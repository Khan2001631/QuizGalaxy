package com.khan.quiz.quiz.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FinishQuizResponseDTO {
    private Long sessionId;
    private int totalScore;
    private int totalQuestions;
    private int attempted;
    private int correctAnswers;
    private int wrongAnswers;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

