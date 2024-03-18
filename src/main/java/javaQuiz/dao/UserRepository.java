package javaQuiz.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javaQuiz.dto.UserWithAnswerCount;
import javaQuiz.dto.UserWithAnswerCountProjection;
import javaQuiz.model.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	public User findByUsername(String username);
	
	 @Query(value = "SELECT u.* " +
             "FROM user u " +
             "WHERE u.role = 'USER' AND NOT EXISTS( " + //selektuj ko ima netacna resenja
             "    SELECT * FROM users_answer ua " +
             "    JOIN question q ON ua.question_id = q.id " +
             "    WHERE ua.user_id = u.id AND ua.wrong_answer_id IS NOT NULL)", nativeQuery = true)
	 public List<User> findUsersWithCorrectAnswers();	
	  
	 // Sledeci radi za vise netacnih odgovora:
	 
	 //Podupit proverava da li postoji bilo koje pitanje na koje korisnik nije odgovorio.
	 //LEFT JOIN korak proverava da li postoji odgovarajući zapis u tabeli question 
	 //   za svako pitanje u tabeli users_answer
	 // napravice se nova velika tabela i u njoj ce  za pitanja kojih 
	 // nema za user-a u users_answers , question_id biti NULL
	 //WHERE... - da li postoji pitanja za koja nisu upisana u users_answer
	 
	 @Query(value ="SELECT u.* " +
		"FROM user u " +
		"WHERE NOT EXISTS ( " +
		 " SELECT * " +
		 " FROM question q " +
		 " LEFT JOIN users_answer ua ON ua.user_id = u.id AND ua.question_id = q.id " +
		 " WHERE ua.question_id IS NULL )", nativeQuery = true)
	 public List<User> findUsersWithAllAnswers();
	 
	 @Query(value = "SELECT u.*, COUNT(ua.user_id) AS answerCount " +
		        "FROM user u " +
		        "JOIN users_answer ua ON ua.user_id = u.id " +
		        "WHERE ua.wrong_answer_id IS NULL " +
		        "GROUP BY u.id " +
		        "ORDER BY answerCount DESC",
		        nativeQuery = true) 
	 List<Object[]> findBestUsers(); 
	 
	 @Query(value = "SELECT u.*, COUNT(ua.user_id) AS answerCount " +
		        "FROM user u " +
		        "JOIN users_answer ua ON ua.user_id = u.id " +
		        "WHERE ua.wrong_answer_id IS NULL " +
		        "GROUP BY u.id " +
		        "ORDER BY answerCount DESC " +
		        "LIMIT 5 ",
		        nativeQuery = true) 
	 List<Object[]> find5BestUsers();
}
