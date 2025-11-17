package com.khan.quiz.quiz.service;

import com.khan.quiz.quiz.dto.Question2DTO;
import com.khan.quiz.quiz.model.Question2;
import com.khan.quiz.quiz.model.Topic2;
import com.khan.quiz.quiz.respository.Topic2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Question2Service {

    @Autowired
    Topic2Repository topic2Repository;

    public ResponseEntity<?> saveQuestion(List<Question2DTO> questionList) {
        try {
            for (Question2DTO question2DTO : questionList) {
                String topicName = question2DTO.getTopic();

                // Check if topic exists, create if not
                Topic2 topic = topic2Repository.findByTopicName(topicName);
                if (topic == null) {
                    topic = new Topic2();
                    topic.setTopicName(topicName);
                    topic.setQuestionList(new ArrayList<>());
                }

                Question2 question = new Question2();
                question.setQuestion(question2DTO.getQuestion());
                question.setCategory(question2DTO.getCategory());
                question.setOption1(question2DTO.getOption1());
                question.setOption2(question2DTO.getOption2());
                question.setOption3(question2DTO.getOption3());
                question.setOption4(question2DTO.getOption4());
                question.setCorrectAnswer(question2DTO.getCorrectAnswer());

                // Maintain both sides of relationship
                question.setTopic(topic);
                topic.getQuestionList().add(question);

                // Save topic (cascade saves question)
                topic2Repository.save(topic);
            }
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Error e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public List<Question2> getQuestionByTopic(String topic) {
        Topic2 topic2 =  topic2Repository.findByTopicName(topic);
        if(topic2 == null){
            return new ArrayList<>();
        }

        return topic2.getQuestionList();
    }
}
