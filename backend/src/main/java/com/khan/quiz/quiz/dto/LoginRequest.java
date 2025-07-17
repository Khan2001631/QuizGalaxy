package com.khan.quiz.quiz.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email; // assuming login via email
    private String password;
}
