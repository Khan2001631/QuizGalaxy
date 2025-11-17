package com.khan.quiz.quiz.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String category;
    @ManyToOne
    @JsonBackReference
    Topic2 topic;
    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    String correctAnswer;
}
