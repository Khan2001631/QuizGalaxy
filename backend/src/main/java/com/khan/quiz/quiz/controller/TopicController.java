package com.khan.quiz.quiz.controller;

import com.khan.quiz.quiz.dto.ApiResponse;
import com.khan.quiz.quiz.dto.TopicDTO;
import com.khan.quiz.quiz.model.Topic;
import com.khan.quiz.quiz.service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Topic>>> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return ResponseEntity.ok(ApiResponse.success(200, "Topics fetched successfully", topics));
    }


    @PostMapping
    public ResponseEntity<ApiResponse<Topic>> createTopic(@Valid @RequestBody TopicDTO topicDTO) {
        Topic created = topicService.createTopic(topicDTO);
        return ResponseEntity.ok(
                ApiResponse.success(200, "Topic created successfully", created)
        );
    }
}
