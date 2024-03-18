package javaQuiz.dto;
import javaQuiz.model.User;

//@Entity(name="user_with_answer_count") //radi samo za prave tabele u bazi 
public class UserWithAnswerCount  {

	private User user;
	private int answerCount;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}
}
