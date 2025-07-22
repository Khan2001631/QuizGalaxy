package com.khan.quiz.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionDTO {

    @NotBlank(message = "Option text cannot be blank")
    private String optionText;

    private boolean isCorrect;
}
