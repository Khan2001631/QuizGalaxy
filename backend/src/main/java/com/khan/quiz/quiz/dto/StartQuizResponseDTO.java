package com.khan.quiz.quiz.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class StartQuizResponseDTO {
    private Long sessionId;
    private List<QuestionDTO> questions;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int durationInSeconds;
}
