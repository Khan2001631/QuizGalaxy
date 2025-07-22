package com.khan.quiz.quiz.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponseDTO {
    private Long id;
    private String questionText;
    private String questionType;
    private String topicName;
    private String difficultyLevel;
    private String createdBy;
    private LocalDateTime createdAt;
    private List<OptionDTO> options;
}
