package com.sapsf.quiz.service;

import com.sapsf.quiz.dto.AnswerDTO;
import com.sapsf.quiz.dto.AnsweredQuestionDTO;
import com.sapsf.quiz.dto.SessionResultDTO;
import com.sapsf.quiz.dto.SessionScoreDTO;
import com.sapsf.quiz.model.Question;
import com.sapsf.quiz.model.QuizAnswer;
import com.sapsf.quiz.model.QuizSession;
import com.sapsf.quiz.repository.QuestionRepository;
import com.sapsf.quiz.repository.QuizAnswerRepository;
import com.sapsf.quiz.repository.QuizSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuestionRepository questionRepository;
    private final QuizSessionRepository quizSessionRepository;
    private final QuizAnswerRepository quizAnswerRepository;

    public QuizService(QuestionRepository questionRepository,
                       QuizSessionRepository quizSessionRepository,
                       QuizAnswerRepository quizAnswerRepository) {
        this.questionRepository = questionRepository;
        this.quizSessionRepository = quizSessionRepository;
        this.quizAnswerRepository = quizAnswerRepository;
    }

    public SessionScoreDTO saveSession(SessionResultDTO dto) {
        // 1. Fetch all Question entities by IDs from dto.getAnswers()
        List<Integer> questionIds = dto.getAnswers().stream()
                .map(AnswerDTO::getQuestionId)
                .collect(Collectors.toList());

        List<Question> questionEntities = questionRepository.findAllById(questionIds);
        Map<Integer, Question> questionMap = questionEntities.stream()
                .collect(Collectors.toMap(Question::getId, q -> q));

        // 2. Create and save QuizSession
        LocalDateTime now = LocalDateTime.now();
        QuizSession session = new QuizSession();
        session.setTopic(dto.getTopic());
        session.setTotalQuestions(dto.getAnswers().size());
        session.setCorrectCount(0);
        session.setStartedAt(now);
        session.setCompletedAt(now);
        session = quizSessionRepository.save(session);

        // 3. For each AnswerDTO: find the Question, compare selectedOption with correct, create and save QuizAnswer
        int correctCount = 0;
        List<AnsweredQuestionDTO> answeredQuestions = new ArrayList<>();

        for (AnswerDTO answerDTO : dto.getAnswers()) {
            Question question = questionMap.get(answerDTO.getQuestionId());
            if (question == null) {
                continue;
            }

            char selected = Character.toUpperCase(answerDTO.getSelectedOption());
            char correct = Character.toUpperCase(question.getCorrectOption());
            boolean isCorrect = selected == correct;

            if (isCorrect) {
                correctCount++;
            }

            QuizAnswer quizAnswer = new QuizAnswer();
            quizAnswer.setSession(session);
            quizAnswer.setQuestion(question);
            quizAnswer.setSelectedOption(selected);
            quizAnswer.setCorrect(isCorrect);
            quizAnswer.setAnsweredAt(now);
            quizAnswerRepository.save(quizAnswer);

            // 5. Build AnsweredQuestionDTO
            AnsweredQuestionDTO answeredDTO = new AnsweredQuestionDTO(
                    question.getId(),
                    question.getTopic(),
                    question.getSubtopic(),
                    question.getQuestionText(),
                    question.getOptionA(),
                    question.getOptionB(),
                    question.getOptionC(),
                    question.getOptionD(),
                    question.getCorrectOption(),
                    question.getExplanationA(),
                    question.getExplanationB(),
                    question.getExplanationC(),
                    question.getExplanationD()
            );
            answeredQuestions.add(answeredDTO);
        }

        // 4. Update session.correctCount and save again
        session.setCorrectCount(correctCount);
        quizSessionRepository.save(session);

        // 6. Return SessionScoreDTO
        return new SessionScoreDTO(correctCount, dto.getAnswers().size(), answeredQuestions);
    }
}
