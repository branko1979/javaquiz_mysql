package javaQuiz.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javaQuiz.model.UsersAnswer;

public interface UsersAnswerRepository extends CrudRepository<UsersAnswer,Integer>{
	@Query(value = "SELECT * FROM users_answer WHERE user_id = ?1 AND question_id = ?2", nativeQuery = true)
    List<UsersAnswer> findUsersAnswerByUserIdAndQuestionId(int userId, int questionId);
}
