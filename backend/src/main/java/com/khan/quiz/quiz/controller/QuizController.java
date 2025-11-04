package com.khan.quiz.quiz.controller;

import com.khan.quiz.quiz.dto.*;
import com.khan.quiz.quiz.mapper.QuizMapper;
import com.khan.quiz.quiz.model.User;
import com.khan.quiz.quiz.model.UserQuizQuestion;
import com.khan.quiz.quiz.respository.UserQuizQuestionRepository;
import com.khan.quiz.quiz.respository.UserRepository;
import com.khan.quiz.quiz.service.QuizSessionService;
import com.khan.quiz.quiz.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizSessionService quizSessionService;
    private final UserRepository userRepository;
    private final UserQuizQuestionRepository userQuizQuestionRepository;
    private final QuizMapper quizMapper;

    private final JwtUtil jwtUtil;

    @PostMapping("/start")
    public ResponseEntity<StartQuizResponseDTO> startQuiz(
            @RequestBody StartQuizRequestDTO request
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElse(null); // or handle guest mode logic

        Long userId = (user != null) ? user.getId() : null;
        StartQuizResponseDTO response = quizSessionService.startQuiz(request, userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submit")
    public ResponseEntity<SubmitAnswerResponseDTO> submitAnswer(@RequestBody SubmitAnswerRequestDTO request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElse(null); // or handle guest mode logic

        Long userId = (user != null) ? user.getId() : null;
        SubmitAnswerResponseDTO response = quizSessionService.submitAnswer(request, userId);
        return ResponseEntity.ok(response);
    }

    // Finish quiz
    @PostMapping("/finish")
    public ResponseEntity<FinishQuizResponseDTO> finishQuiz(@RequestBody FinishQuizRequestDTO request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElse(null); // or handle guest mode logic

        Long userId = (user != null) ? user.getId() : null;
        FinishQuizResponseDTO response = quizSessionService.finishQuiz(request, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-sessions")
    public ResponseEntity<List<QuizSummaryDTO>> getMyQuizSessions() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElse(null); // or handle guest mode logic

        Long userId = (user != null) ? user.getId() : null;
        List<QuizSummaryDTO> sessions = quizSessionService.getQuizHistoryForUser(userId);
        return ResponseEntity.ok(sessions);
    }

//    @GetMapping("/questions/{sessionId}")
//    public ResponseEntity<List<UserQuizQuestionDTO>> getQuizQuestions(@PathVariable Long sessionId) {
//        List<UserQuizQuestion> userQuizQuestions = userQuizQuestionRepository
//                .findBySessionIdOrderByQuestionOrderAsc(sessionId);
//
//        List<UserQuizQuestionDTO> dtos = userQuizQuestions.stream()
//                .map(quizMapper::toUserQuizQuestionDTO)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(dtos);
//    }




}