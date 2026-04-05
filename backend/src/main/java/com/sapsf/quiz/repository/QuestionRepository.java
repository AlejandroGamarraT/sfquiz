package com.sapsf.quiz.repository;

import com.sapsf.quiz.dto.TopicDTO;
import com.sapsf.quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query("SELECT new com.sapsf.quiz.dto.TopicDTO(q.topic, COUNT(q)) FROM Question q GROUP BY q.topic ORDER BY q.topic")
    List<TopicDTO> findTopicsWithCount();

    @Query(value = "SELECT * FROM questions WHERE topic = :topic ORDER BY RAND() LIMIT :limit",
           nativeQuery = true)
    List<Question> findRandom(@Param("topic") String topic,
                              @Param("limit") int limit);
}
