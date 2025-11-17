package com.khan.quiz.quiz.dto;

import com.khan.quiz.quiz.model.Topic2;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question2DTO {
    String category;
    String topic;
    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    String correctAnswer;
}
