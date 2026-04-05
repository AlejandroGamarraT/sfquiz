package com.sapsf.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionResultDTO {

    private String topic;
    private List<AnswerDTO> answers;
}
