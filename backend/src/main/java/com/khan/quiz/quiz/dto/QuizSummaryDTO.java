package com.khan.quiz.quiz.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class QuizSummaryDTO {
    private Long sessionId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer totalScore;
    private Integer totalQuestions;
    private Integer attempted;
    private Integer correctAnswers;
    private Integer wrongAnswers;
    private Boolean allowSkipping;
}
