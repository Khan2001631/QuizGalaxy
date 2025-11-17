package com.khan.quiz.quiz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserQuizSession session;

    @ManyToOne
    private Question question;

    private String selectedOption; // assuming options are A/B/C/D etc.

    private Boolean isCorrect;

    private LocalDateTime submittedAt;

    @Column(name = "is_skipped")
    private Boolean skipped = false;
}

