package com.khan.quiz.quiz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_quiz_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQuizSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key to User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Column(nullable = false)
    private Integer totalScore;

    // Optional: how many questions were in this quiz
    private Integer totalQuestions;

    // Optional: was it skipped enabled
    private Boolean allowSkipping;

    // Optional: number of questions attempted
    private Integer attempted;

    // Optional: number of correct/wrong
    private Integer correctAnswers;
    private Integer wrongAnswers;

    private Integer skipped = 0;
}
