package com.khan.quiz.quiz.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionRequestDTO {

    @NotNull(message = "Topic ID is required")
    private Long topicId;

    @NotNull(message = "Difficulty ID is required")
    private Long difficultyId;

    @NotBlank(message = "Question text is required")
    private String questionText;

    private String questionType = "MCQ"; // Default "MCQ"

    @Size(min = 2, message = "At least two options are required")
    private List<OptionDTO> options;
}
