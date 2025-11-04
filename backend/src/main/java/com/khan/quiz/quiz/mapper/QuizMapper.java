package com.khan.quiz.quiz.mapper;

import com.khan.quiz.quiz.dto.QuestionDTO;
import com.khan.quiz.quiz.dto.QuizOptionDTO;
import com.khan.quiz.quiz.dto.UserQuizQuestionDTO;
import com.khan.quiz.quiz.model.Question;
import com.khan.quiz.quiz.model.UserQuizQuestion;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuizMapper {
    public QuestionDTO toQuestionDTO(Question question) {
        List<QuizOptionDTO> optionDTOs = question.getOptions().stream().map(opt -> {
            QuizOptionDTO dto = new QuizOptionDTO();
            dto.setId(opt.getId());
            dto.setOptionText(opt.getOptionText());
            dto.setOptionOrder(opt.getOptionOrder());
            return dto;
        }).collect(Collectors.toList());

        Collections.shuffle(optionDTOs); // Shuffle the options here

        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setQuestionText(question.getQuestionText());
        dto.setDifficultyLevel(question.getDifficulty().getLevel().name());
        dto.setOptions(optionDTOs);
        return dto;
    }

    public UserQuizQuestionDTO toUserQuizQuestionDTO(UserQuizQuestion userQuizQuestion) {
        Question question = userQuizQuestion.getQuestion();

        List<QuizOptionDTO> optionDTOs = question.getOptions().stream().map(opt -> {
            QuizOptionDTO dto = new QuizOptionDTO();
            dto.setId(opt.getId());
            dto.setOptionText(opt.getOptionText());
            dto.setOptionOrder(opt.getOptionOrder());
            return dto;
        }).collect(Collectors.toList());

        Collections.shuffle(optionDTOs); // Shuffle the options

        return UserQuizQuestionDTO.builder()
                .questionId(question.getId())
                .questionText(question.getQuestionText())
                .difficultyLevel(question.getDifficulty().getLevel().name())
                .questionOrder(userQuizQuestion.getQuestionOrder())
                .answered(Boolean.TRUE.equals(userQuizQuestion.getAnswered()))
                .skipped(Boolean.TRUE.equals(userQuizQuestion.getSkipped()))
                .options(optionDTOs)
                .build();
    }

}
