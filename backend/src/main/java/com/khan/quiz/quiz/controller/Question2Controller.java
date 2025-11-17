package com.khan.quiz.quiz.controller;


import com.khan.quiz.quiz.dto.Question2DTO;
import com.khan.quiz.quiz.model.Question2;
import com.khan.quiz.quiz.service.Question2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Question2Controller {


    @Autowired
    Question2Service question2Service;


    @PostMapping("/create/questions")
    public ResponseEntity<?> saveQuestions(@RequestBody List<Question2DTO> questionList){
        return question2Service.saveQuestion(questionList);


    }

    @GetMapping("/get/questions/{topic}")
    public List<Question2> getQuestionByTopic(@PathVariable("topic") String topic){
        return question2Service.getQuestionByTopic(topic);


    }

}
