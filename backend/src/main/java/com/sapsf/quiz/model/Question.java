package com.sapsf.quiz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String topic;

    private String subtopic;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "option_a")
    private String optionA;

    @Column(name = "option_b")
    private String optionB;

    @Column(name = "option_c")
    private String optionC;

    @Column(name = "option_d")
    private String optionD;

    @Column(name = "correct_option")
    private Character correctOption;

    @Column(name = "explanation_a")
    private String explanationA;

    @Column(name = "explanation_b")
    private String explanationB;

    @Column(name = "explanation_c")
    private String explanationC;

    @Column(name = "explanation_d")
    private String explanationD;
}
