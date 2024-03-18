package javaQuiz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javaQuiz.dao.WrongAnswerRepository;
import javaQuiz.model.WrongAnswer;
@Service
@Transactional
public class WrongAnswerService {
	private final WrongAnswerRepository wrongAnswerRepository ;
	
	public WrongAnswerService(WrongAnswerRepository wrongAnswerRepository) {
		this.wrongAnswerRepository = wrongAnswerRepository;
	}

	public List<WrongAnswer> findByQuestion_id(int question_id){
		return  wrongAnswerRepository.findByQuestionId(question_id);
	}
	
	public void save(WrongAnswer wrongAnswer) {
		wrongAnswerRepository.save(wrongAnswer);
		
	}

	public void deleteByQuestionId(int id) {
		wrongAnswerRepository.deleteByQuestionId(id);
		
	}

	public void delete(int id) {
		wrongAnswerRepository.deleteById(id);
		
	}

	public WrongAnswer find(int id) {
		Optional<WrongAnswer> optionalWrongAnsver =  wrongAnswerRepository.findById(id);
		return optionalWrongAnsver.get();
	}
}
