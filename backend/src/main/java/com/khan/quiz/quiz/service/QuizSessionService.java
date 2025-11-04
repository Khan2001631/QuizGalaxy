package com.khan.quiz.quiz.service;

import com.khan.quiz.quiz.dto.*;
import com.khan.quiz.quiz.mapper.QuizMapper;
import com.khan.quiz.quiz.model.*;
import com.khan.quiz.quiz.respository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizSessionService {

    private final QuestionRepository questionRepository;
    private final UserQuizSessionRepository sessionRepository;
    private final QuizMapper quizMapper; // Converts entities to DTOs
    private final UserRepository userRepository;
//    private final UserAnswerRepository userAnswerRepository;
    private final OptionRepository optionRepository;
    private final UserQuizQuestionRepository userQuizQuestionRepository;

    public StartQuizResponseDTO startQuiz(StartQuizRequestDTO request, Long userId) {
        List<Question> selectedQuestions = questionRepository.findRandomQuestions(
                request.getTopicIds(),
                request.getDifficultyLevels(),
                (Pageable) PageRequest.of(0, request.getTotalQuestions())
        );

        if (selectedQuestions.isEmpty()) {
            throw new RuntimeException("No questions available for the selected filters.");
        }

        // This can be changed later depending on whether we will introduce "Challenge Mode" (reduced time) if enabled in future
        int durationInSeconds = selectedQuestions.size() * 30;

        UserQuizSession session = UserQuizSession.builder()
                .user(userId != null ? userRepository.findById(userId).orElse(null) : null)
                .startTime(LocalDateTime.now())
                .totalQuestions(selectedQuestions.size())
                .totalScore(0)
                .attempted(0)
                .correctAnswers(0)
                .wrongAnswers(0)
                .allowSkipping(request.isAllowSkipping())
                .endTime(LocalDateTime.now().plusSeconds(durationInSeconds))
                .build();

        sessionRepository.save(session);

        List<UserQuizQuestion> userQuizQuestions = new ArrayList<>();

        int order = 1;
        for (Question q : selectedQuestions) {
            userQuizQuestions.add(UserQuizQuestion.builder()
                    .session(session)
                    .question(q)
                    .questionOrder(order++)
                    .build());
        }
        userQuizQuestionRepository.saveAll(userQuizQuestions);

        List<QuestionDTO> questionDTOs = selectedQuestions.stream()
                .map(quizMapper::toQuestionDTO)
                .toList();


        return StartQuizResponseDTO.builder()
                .sessionId(session.getId())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .questions(questionDTOs)
                .build();
    }

    public SubmitAnswerResponseDTO submitAnswer(SubmitAnswerRequestDTO request, Long userId) {
        UserQuizSession session = sessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new RuntimeException("Quiz session not found"));

        if (!session.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to session");
        }

        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Handle skipped question
        if (request.getSelectedOptionId() == null) {
            session.setAttempted(session.getAttempted() + 1);
            sessionRepository.save(session);

//            UserQuizQuestion userQuizQuestion = userQuizQuestionRepository
//                    .findBySessionAndQuestion(session, question)
//                    .orElseThrow(() -> new RuntimeException("Question not assigned in session"));
//
//            userQuizQuestion.setSelectedOption(selectedOption);
//            userQuizQuestion.setAnswered(true);
//            userQuizQuestion.setIsCorrect(isCorrect);
//            userQuizQuestion.setSubmittedAt(LocalDateTime.now());
//
//            userQuizQuestionRepository.save(userQuizQuestion);
//
//
//            UserAnswer userAnswer = UserAnswer.builder()
//                    .session(session)
//                    .question(question)
//                    .selectedOption(null)
//                    .isCorrect(false)
//                    .submittedAt(LocalDateTime.now())
//                    .build();
//
//
//
//            userAnswerRepository.save(userAnswer);

            return SubmitAnswerResponseDTO.builder()
                    .isCorrect(false)
                    .updatedScore(session.getTotalScore())
                    .attempted(session.getAttempted())
                    .remainingQuestions(session.getTotalQuestions() - session.getAttempted())
                    .build();
        }

        Option selectedOption = optionRepository.findById(request.getSelectedOptionId())
                .orElseThrow(() -> new RuntimeException("Selected option not found"));

        Optional<Option> correctOption = question.getOptions().stream()
                .filter(Option::getIsCorrect)
                .findFirst();

        boolean isCorrect = correctOption
                .map(opt -> opt.getId().equals(selectedOption.getId()))
                .orElse(false);

        // Update session scores
        session.setAttempted(session.getAttempted() + 1);
        if (isCorrect) {
            session.setCorrectAnswers(session.getCorrectAnswers() + 1);
            session.setTotalScore(session.getTotalScore() + 3);
        } else {
            session.setWrongAnswers(session.getWrongAnswers() + 1);
            session.setTotalScore(session.getTotalScore() - 1);
        }

        // Save user answer
        UserAnswer userAnswer = UserAnswer.builder()
                .session(session)
                .question(question)
                .selectedOption(selectedOption.getOptionText())
                .isCorrect(isCorrect)
                .submittedAt(LocalDateTime.now())
                .build();

//        userAnswerRepository.save(userAnswer);
        sessionRepository.save(session);

        return SubmitAnswerResponseDTO.builder()
                .isCorrect(isCorrect)
                .updatedScore(session.getTotalScore())
                .attempted(session.getAttempted())
                .remainingQuestions(session.getTotalQuestions() - session.getAttempted())
                .build();
    }


//    public FinishQuizResponseDTO finishQuiz(FinishQuizRequestDTO request, Long userId) {
//        UserQuizSession session = sessionRepository.findById(request.getSessionId())
//                .orElseThrow(() -> new RuntimeException("Quiz session not found"));
//
//        if (!session.getUser().getId().equals(userId)) {
//            throw new RuntimeException("Unauthorized access");
//        }
//
//        if (session.getEndTime() == null) {
//            session.setEndTime(LocalDateTime.now());
//            sessionRepository.save(session);
//        }
//
//        return FinishQuizResponseDTO.builder()
//                .sessionId(session.getId())
//                .totalScore(session.getTotalScore())
//                .totalQuestions(session.getTotalQuestions())
//                .attempted(session.getAttempted())
//                .correctAnswers(session.getCorrectAnswers())
//                .wrongAnswers(session.getWrongAnswers())
//                .startTime(session.getStartTime())
//                .endTime(session.getEndTime())
//                .build();
//    }

    public FinishQuizResponseDTO finishQuiz(FinishQuizRequestDTO request, Long userId) {
        UserQuizSession session = sessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new RuntimeException("Quiz session not found"));

        if (!session.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        int attempted = 0, correct = 0, wrong = 0, skipped = 0, score = 0;

        for (FinishQuizRequestDTO.AnswerDTO ans : request.getAnswers()) {
            Question question = questionRepository.findById(ans.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            UserQuizQuestion userQuizQuestion = userQuizQuestionRepository
                    .findBySessionAndQuestion(session, question)
                    .orElseThrow(() -> new RuntimeException("Question not assigned in session"));

            userQuizQuestion.setAnswered(ans.getSelectedOptionId() != null);
            userQuizQuestion.setSubmittedAt(LocalDateTime.now());

            if (ans.getSelectedOptionId() == null) {
                skipped++;
                userQuizQuestion.setSkipped(true);
                userQuizQuestion.setIsCorrect(false);
            } else {
                attempted++;
                Option selectedOption = optionRepository.findById(ans.getSelectedOptionId())
                        .orElseThrow(() -> new RuntimeException("Option not found"));

                userQuizQuestion.setSelectedOption(selectedOption);

                boolean isCorrect = selectedOption.getIsCorrect();
                userQuizQuestion.setIsCorrect(isCorrect);

                if (isCorrect) {
                    correct++;
                    score += 3;
                } else {
                    wrong++;
                    score -= 1;
                }
            }

            userQuizQuestionRepository.save(userQuizQuestion);
        }

        // update session stats
        session.setAttempted(attempted + skipped);
        session.setCorrectAnswers(correct);
        session.setWrongAnswers(wrong);
        session.setSkipped(skipped);
        session.setTotalScore(score);
        session.setEndTime(LocalDateTime.now());

        sessionRepository.save(session);

        return FinishQuizResponseDTO.builder()
                .sessionId(session.getId())
                .totalScore(score)
                .totalQuestions(session.getTotalQuestions())
                .attempted(attempted + skipped)
                .correctAnswers(correct)
                .wrongAnswers(wrong)
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .build();
    }

    public List<QuizSummaryDTO> getQuizHistoryForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserQuizSession> sessions = sessionRepository.findByUserOrderByStartTimeDesc(user);

        return sessions.stream().map(session -> QuizSummaryDTO.builder()
                .sessionId(session.getId())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .totalScore(session.getTotalScore())
                .totalQuestions(session.getTotalQuestions())
                .attempted(session.getAttempted())
                .correctAnswers(session.getCorrectAnswers())
                .wrongAnswers(session.getWrongAnswers())
                .allowSkipping(session.getAllowSkipping())
                .build()
        ).collect(Collectors.toList());
    }

}
