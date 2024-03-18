package javaQuiz.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javaQuiz.dao.QuestionRepository;
import javaQuiz.dto.QuestionWithAnswerCount;
import javaQuiz.model.Question;
import javaQuiz.model.WrongAnswer;

@Service
@Transactional
public class QuestionService {
	public final QuestionRepository questionRepository ;
	public WrongAnswerService wrongAnswerService;

	public QuestionService(QuestionRepository questionRepository, WrongAnswerService wrongAnswerService) {
		super();
		this.questionRepository = questionRepository;
		this.wrongAnswerService = wrongAnswerService;
	}

	public Map<Question,List<WrongAnswer>> findAllQueAndA(){
		Map<Question,List<WrongAnswer>> questionsAndAnswers = new HashMap<>();
		for (Question question : questionRepository.findAll()) {
			int pitId = question.getId();
			questionsAndAnswers.put(question,wrongAnswerService.findByQuestion_id(pitId));
		}
		 for (Map.Entry<Question,List<WrongAnswer>> question : questionsAndAnswers.entrySet()) {
			System.out.println("pitanje:  "+ question.getKey().getText());
			System.out.println("ispravan odgovor:  "+ question.getKey().getTrueAnswer());
			for (WrongAnswer wrongAnswer : question.getValue()) {
				System.out.println("  neispravno: "+ wrongAnswer.getText());
			}
		}
		return questionsAndAnswers;	
	}
	
	public void save(Question question) {
		questionRepository.save(question);		
	}
	
	public Question findQuestion(int id) {
		Optional<Question> optionalQuestion = questionRepository.findById(id);
		return optionalQuestion.get();
	}
	
	public void delete(int id) {
		questionRepository.deleteById(id);
	}
	
	public List<QuestionWithAnswerCount> questionsCorrectPercentage(){
		List<Object[]> rows = questionRepository.questionsCorrectPercentage();
		List<QuestionWithAnswerCount> questionsWithAnswerCounts = new ArrayList<>();
		
		for (Object[] row :rows) {
			 Question question = find((int)row[0]);
			 
			 QuestionWithAnswerCount questionWithAnswerCount = new QuestionWithAnswerCount();
			 
			 questionWithAnswerCount.setQuestion(question);
			 questionWithAnswerCount.setQuestionCount( ((Long)row[1]).intValue());
			 questionWithAnswerCount.setCorrectAnswerCount(((Long) row[2]).intValue());
			 questionWithAnswerCount.setPercentage(((BigDecimal) row[3]).floatValue());
			 
			 questionsWithAnswerCounts.add(questionWithAnswerCount);
		}
		return questionsWithAnswerCounts;
	}
	

	
	public List<QuestionWithAnswerCount> questionsIncorrectPercentage(){
		List<Object[]> rows = questionRepository.questionsIncorrectPercentage();
		List<QuestionWithAnswerCount> questionsWithAnswerCounts = new ArrayList<>();
		
		for (Object[] row :rows) {
			 Question question = find((int)row[0]);
			 
			 QuestionWithAnswerCount questionWithAnswerCount = new QuestionWithAnswerCount();
			 
			 questionWithAnswerCount.setQuestion(question);
			 questionWithAnswerCount.setQuestionCount( ((Long)row[1]).intValue());
			 questionWithAnswerCount.setCorrectAnswerCount(((Long) row[2]).intValue());
			 questionWithAnswerCount.setPercentage(((BigDecimal) row[3]).floatValue());
			 
			 questionsWithAnswerCounts.add(questionWithAnswerCount);
		}
		return questionsWithAnswerCounts;
	}
	
	public List<WrongAnswer> allWrongAnForQue(int pitId){
		return wrongAnswerService.findByQuestion_id(pitId);
	}
	 
	public List<Question> findAll(){
		return (List<Question>) questionRepository.findAll();
	}
	
	public Question find(int id) {
		Optional<Question> optionalWrongAnsver =  questionRepository.findById(id);
		return optionalWrongAnsver.get();
	}
}
