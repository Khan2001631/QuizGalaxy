package com.khan.quiz.quiz.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(length = 10)
    private String gender = "M";

    @Column(name = "user_type", length = 20)
    private String userType;

    @Column(name = "user_level", length = 45)
    private String userLevel;

    @Column(length = 10)
    private String otp;

    @Column(name = "otp_status", length = 45)
    private String otpStatus;

    @Column(name = "quizzes_attended_count")
    private Integer quizzesAttendedCount = 0;

    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
