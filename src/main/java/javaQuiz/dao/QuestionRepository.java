package javaQuiz.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javaQuiz.model.Question;

public interface QuestionRepository extends CrudRepository<Question,Integer>{
	
	@Query ( value ="SELECT q1.id, q1.questionCount1, q2.questionCount2, (q2.questionCount2 / q1.questionCount1) * 100 AS questionCount " +
	"FROM " +
	"(SELECT q.id, COUNT(ua.id) AS questionCount1 " +
	"FROM question q "+
	"JOIN users_answer ua ON q.id = ua.question_id " +
	"GROUP BY q.id) AS q1 " +
	"JOIN " +
	"(SELECT q.id, COUNT(ua.id) AS questionCount2 " +
	"FROM question q " +
	"JOIN users_answer ua ON q.id = ua.question_id " +
	"WHERE ua.wrong_answer_id IS NULL " +
	"GROUP BY q.id) AS q2 " +
	"ON q1.id = q2.id " +
	"ORDER BY questionCount DESC ",
	nativeQuery = true)
	List<Object[]> questionsCorrectPercentage();
	
	@Query ( value ="SELECT q1.id, q1.questionCount1, q2.questionCount2, (q2.questionCount2 / q1.questionCount1) * 100 AS questionCount " +
	"FROM " +
	"(SELECT q.id, COUNT(ua.id) AS questionCount1 " +
	"FROM question q "+
	"JOIN users_answer ua ON q.id = ua.question_id " +
	"GROUP BY q.id) AS q1 " +
	"JOIN " +
	"(SELECT q.id, COUNT(ua.id) AS questionCount2 " +
	"FROM question q " +
	"JOIN users_answer ua ON q.id = ua.question_id " +
	"WHERE ua.wrong_answer_id IS not NULL " +
	"GROUP BY q.id) AS q2 " +
	"ON q1.id = q2.id " +
	"ORDER BY questionCount DESC ",
	nativeQuery = true)
	List<Object[]> questionsIncorrectPercentage();
}
