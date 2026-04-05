package com.sapsf.quiz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "quiz_answers")
public class QuizAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private QuizSession session;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "selected_option")
    private Character selectedOption;

    @Column(name = "is_correct")
    private boolean correct;

    @Column(name = "answered_at")
    private LocalDateTime answeredAt;
}
