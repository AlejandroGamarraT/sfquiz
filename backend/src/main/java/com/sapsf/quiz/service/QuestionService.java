package com.sapsf.quiz.service;

import com.sapsf.quiz.dto.QuestionDTO;
import com.sapsf.quiz.dto.TopicDTO;
import com.sapsf.quiz.model.Question;
import com.sapsf.quiz.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<TopicDTO> getTopics() {
        return questionRepository.findTopicsWithCount();
    }

    public List<QuestionDTO> getQuestions(String topic, int limit) {
        List<Question> questions = questionRepository.findRandom(topic, limit);
        return questions.stream()
                .map(q -> new QuestionDTO(
                        q.getId(),
                        q.getTopic(),
                        q.getSubtopic(),
                        q.getQuestionText(),
                        q.getOptionA(),
                        q.getOptionB(),
                        q.getOptionC(),
                        q.getOptionD(),
                        q.getCorrectOption(),
                        q.getExplanationA(),
                        q.getExplanationB(),
                        q.getExplanationC(),
                        q.getExplanationD()
                ))
                .collect(Collectors.toList());
    }
}
