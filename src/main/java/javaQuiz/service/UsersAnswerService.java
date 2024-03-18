package javaQuiz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javaQuiz.dao.UsersAnswerRepository;
import javaQuiz.model.UsersAnswer;



@Service
@Transactional
public class UsersAnswerService {
	public final  UsersAnswerRepository usersAnswerRepository;
	
	public UsersAnswerService(UsersAnswerRepository usersAnswerRepository) {
		this.usersAnswerRepository = usersAnswerRepository;
	}


	public List<UsersAnswer> findUsersAnswerByUserIdAndQuestionId(int userId, int questionId) {
		return usersAnswerRepository.findUsersAnswerByUserIdAndQuestionId(userId, questionId);
	}
	
	//public UsersAnswer findUsersAnswerByUsIdQueIdWrAnsNULL(int userId, int questionId) {
		//return usersAnswerRepository.findUsersAnswerByUsIdQueIdWrAnsNULL(userId, questionId);
	//}


	public void save(UsersAnswer existingUsersAnswer) {
		usersAnswerRepository.save(existingUsersAnswer);
		
	}
	
	public void deleteOldAnswers(int userId, int questionId) {
		List<UsersAnswer> oldAnswers = findUsersAnswerByUserIdAndQuestionId(userId,questionId);
		for (UsersAnswer oldAnswer : oldAnswers) {
			usersAnswerRepository.deleteById(oldAnswer.getId());
		}
	}
	
}
