package com.khan.quiz.quiz.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuizSessionResponseDTO {
    private Long sessionId;
    private Long topicId;
    private String topicName;
    private LocalDateTime startTime;
    private int durationInSeconds;
    private boolean allowSkipping;
    private List<QuestionDTO> questions;
}
