package javaQuiz.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import javaQuiz.model.WrongAnswer;

public interface WrongAnswerRepository extends CrudRepository<WrongAnswer, Integer>{
	public   List<WrongAnswer> findByQuestionId(int question_id) ;
	
	public void deleteByQuestionId(int id);
}
