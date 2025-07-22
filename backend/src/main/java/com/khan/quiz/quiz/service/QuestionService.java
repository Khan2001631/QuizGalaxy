package com.khan.quiz.quiz.service;

import com.khan.quiz.quiz.dto.OptionDTO;
import com.khan.quiz.quiz.dto.QuestionRequestDTO;
import com.khan.quiz.quiz.dto.QuestionResponseDTO;
import com.khan.quiz.quiz.model.*;
import com.khan.quiz.quiz.respository.DifficultyLevelRepository;
import com.khan.quiz.quiz.respository.QuestionRepository;
import com.khan.quiz.quiz.respository.TopicRepository;
import com.khan.quiz.quiz.respository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TopicRepository topicRepository;
    private final DifficultyLevelRepository difficultyRepository;
    private final UserRepository userRepository;

    public QuestionResponseDTO createQuestion(QuestionRequestDTO questionRequestDTO, Long userId) {
        Topic topic = topicRepository.findById(questionRequestDTO.getTopicId())
                .orElseThrow(() -> new EntityNotFoundException("Topic not found"));

        DifficultyLevel difficultyLevel = difficultyRepository.findById(questionRequestDTO.getDifficultyId())
                .orElseThrow(() -> new EntityNotFoundException("Difficulty level not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Question question = Question.builder()
                .questionText(questionRequestDTO.getQuestionText())
                .questionType(questionRequestDTO.getQuestionType())
                .topic(topic)
                .difficulty(difficultyLevel)
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .build();

        question.setOptions(questionRequestDTO.getOptions().stream().map(optionDTO -> Option.builder()
                .optionText(optionDTO.getOptionText())
                .isCorrect(optionDTO.isCorrect())
                .question(question) // set the owning side
                .build()).collect(Collectors.toList()));

        Question savedQuestion = questionRepository.save(question);

        return QuestionResponseDTO.builder()
                .id(savedQuestion.getId())
                .questionText(savedQuestion.getQuestionText())
                .questionType(savedQuestion.getQuestionType())
                .topicName(topic.getName())
                .difficultyLevel(String.valueOf(difficultyLevel.getLevel()))
                .createdBy(user.getEmail())
                .createdAt(savedQuestion.getCreatedAt())
                .options(savedQuestion.getOptions().stream().map(opt ->
                                new OptionDTO(opt.getOptionText(), opt.getIsCorrect()))
                        .collect(Collectors.toList()))
                .build();

    }

    public List<QuestionResponseDTO> getQuestionsByTopic(Long topicId) {
        List<Question> questions = questionRepository.findByTopicId(topicId);

        return questions.stream().map(q -> QuestionResponseDTO.builder()
                .id(q.getId())
                .questionText(q.getQuestionText())
                .questionType(q.getQuestionType())
                .topicName(q.getTopic().getName())
                .difficultyLevel(q.getDifficulty().getLevel().toString())
                .createdBy(q.getCreatedBy().getEmail())
                .createdAt(q.getCreatedAt())
                .options(q.getOptions().stream().map(opt ->
                                new OptionDTO(opt.getOptionText(), opt.getIsCorrect()))
                        .collect(Collectors.toList()))
                .build()
        ).collect(Collectors.toList());
    }

}
