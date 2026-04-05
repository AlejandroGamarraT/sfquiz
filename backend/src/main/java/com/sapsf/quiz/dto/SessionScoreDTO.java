package com.sapsf.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionScoreDTO {

    private int correctCount;
    private int totalQuestions;
    private List<AnsweredQuestionDTO> answeredQuestions;
}
