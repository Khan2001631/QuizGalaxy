package com.khan.quiz.quiz.controller;

import com.khan.quiz.quiz.dto.ApiResponse;
import com.khan.quiz.quiz.dto.QuestionRequestDTO;
import com.khan.quiz.quiz.dto.QuestionResponseDTO;
import com.khan.quiz.quiz.model.User;
import com.khan.quiz.quiz.respository.UserRepository;
import com.khan.quiz.quiz.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> createQuestion(
            @RequestBody QuestionRequestDTO questionRequestDTO
    ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();

            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Long userId = user.getId();

            QuestionResponseDTO createdQuestion = questionService.createQuestion(questionRequestDTO, userId);

            ApiResponse<QuestionResponseDTO> response = new ApiResponse<>(
                    201,
                    "Question created successfully",
                    true,
                    createdQuestion
            );
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            ApiResponse<QuestionResponseDTO> error = new ApiResponse<>(
                    400,
                    "Failed to create question: " + e.getMessage(),
                    false,
                    null
            );
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionResponseDTO>>> getQuestionsByTopic(
            @RequestParam Long topicId
    ) {
        try {
            List<QuestionResponseDTO> questions = questionService.getQuestionsByTopic(topicId);
            ApiResponse<List<QuestionResponseDTO>> response = new ApiResponse<>(
                    200,
                    "Questions fetched successfully",
                    true,
                    questions
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<QuestionResponseDTO>> error = new ApiResponse<>(
                    400,
                    "Failed to fetch questions: " + e.getMessage(),
                    false,
                    null
            );
            return ResponseEntity.badRequest().body(error);
        }
    }

}
