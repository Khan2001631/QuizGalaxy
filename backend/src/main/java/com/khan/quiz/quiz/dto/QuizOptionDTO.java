package com.khan.quiz.quiz.dto;

import lombok.Data;

@Data
public class QuizOptionDTO {
    private Long id;
    private String optionText;
    private Integer optionOrder;
}
