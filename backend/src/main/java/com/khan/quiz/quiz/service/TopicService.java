package com.khan.quiz.quiz.service;

import com.khan.quiz.quiz.dto.TopicDTO;
import com.khan.quiz.quiz.model.Topic;
import com.khan.quiz.quiz.respository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    public Topic createTopic(TopicDTO topicDTO) {
        // Check if topic with same name already exists
        if (topicRepository.existsByNameIgnoreCase(topicDTO.getName())) {
            throw new IllegalArgumentException("Topic with the same name already exists");
        }

        Topic topic = Topic.builder()
                .name(topicDTO.getName())
                .description(topicDTO.getDescription())
                .createdAt(LocalDateTime.now())
                .build();

        return topicRepository.save(topic);
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
}
