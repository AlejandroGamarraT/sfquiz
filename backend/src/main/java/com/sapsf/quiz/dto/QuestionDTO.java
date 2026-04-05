package com.sapsf.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    private Integer id;
    private String topic;
    private String subtopic;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private Character correctOption;
    private String explanationA;
    private String explanationB;
    private String explanationC;
    private String explanationD;
}
