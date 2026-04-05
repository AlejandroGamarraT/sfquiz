package com.sapsf.quiz.repository;

import com.sapsf.quiz.model.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {
}
