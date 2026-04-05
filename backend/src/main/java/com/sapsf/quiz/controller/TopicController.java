package com.sapsf.quiz.controller;

import com.sapsf.quiz.dto.ApiResponse;
import com.sapsf.quiz.dto.TopicDTO;
import com.sapsf.quiz.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final QuestionService questionService;

    public TopicController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TopicDTO>>> getTopics() {
        List<TopicDTO> topics = questionService.getTopics();
        return ResponseEntity.ok(ApiResponse.ok(topics));
    }
}
