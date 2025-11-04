package com.khan.quiz.quiz.dto;

import lombok.Data;

@Data
public class SubmitAnswerRequestDTO {
    private Long sessionId;
    private Long questionId;
    private Long selectedOptionId;
}
