package com.khan.quiz.quiz.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;



@Entity
@Table(name = "user_responses")
public class UserResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK to session
    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private UserQuizSession session;

    // FK to question
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    // FK to selected option (nullable if skipped)
    @ManyToOne
    @JoinColumn(name = "selected_option_id")
    private Option selectedOption;

    private Boolean isCorrect;

    // Whether the user skipped this question
    private Boolean skipped;
}
