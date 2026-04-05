package com.sapsf.quiz.controller;

import com.sapsf.quiz.dto.ApiResponse;
import com.sapsf.quiz.dto.QuestionDTO;
import com.sapsf.quiz.dto.QuizResponseDTO;
import com.sapsf.quiz.dto.SessionResultDTO;
import com.sapsf.quiz.dto.SessionScoreDTO;
import com.sapsf.quiz.service.QuestionService;
import com.sapsf.quiz.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuestionService questionService;
    private final QuizService quizService;

    public QuizController(QuestionService questionService, QuizService quizService) {
        this.questionService = questionService;
        this.quizService = quizService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<QuizResponseDTO>> getQuiz(
            @RequestParam String topic,
            @RequestParam(defaultValue = "10") int limit) {
        List<QuestionDTO> questions = questionService.getQuestions(topic, limit);
        QuizResponseDTO response = new QuizResponseDTO(questions);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PostMapping("/session")
    public ResponseEntity<ApiResponse<SessionScoreDTO>> saveSession(
            @RequestBody SessionResultDTO dto) {
        SessionScoreDTO score = quizService.saveSession(dto);
        return ResponseEntity.ok(ApiResponse.ok(score));
    }
}
