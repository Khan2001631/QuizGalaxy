package com.khan.quiz.quiz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_quiz_questions") // good to specify table name
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private UserQuizSession session;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    private Boolean answered = false;

    private Boolean skipped = false;

    private Boolean isCorrect;

    private LocalDateTime submittedAt;

    @Column(name = "question_order", nullable = false)
    private Integer questionOrder;

    @ManyToOne
    @JoinColumn(name = "selected_option_id")
    private Option selectedOption;
}

