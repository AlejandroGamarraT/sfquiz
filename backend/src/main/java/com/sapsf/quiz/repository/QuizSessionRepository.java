package com.sapsf.quiz.repository;

import com.sapsf.quiz.model.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {
}
