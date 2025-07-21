package com.khan.quiz.quiz.model;

import com.khan.quiz.quiz.enums.Difficulty;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "difficulty_levels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DifficultyLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 20)
    private Difficulty level;

    @OneToMany(mappedBy = "difficulty", cascade = CascadeType.ALL)
    private List<Question> questions;
}
