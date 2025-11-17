package com.khan.quiz.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinishQuizRequestDTO {
    private Long sessionId;
    private List<AnswerDTO> answers; // all submitted answers

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AnswerDTO {
        private Long questionId;
        private Long selectedOptionId; // nullable if skipped
    }
}

