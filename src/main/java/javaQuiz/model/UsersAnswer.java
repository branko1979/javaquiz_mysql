package javaQuiz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity (name = "users_answer")
public class UsersAnswer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int userId;
	private int questionId;
	private Integer wrongAnswerId;
	public UsersAnswer(int id, int userId, int questionId, Integer wrongAnswerId) {
		super();
		this.id = id;
		this.userId = userId;
		this.questionId = questionId;
		this.wrongAnswerId = wrongAnswerId;
	}
	
	public UsersAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getWrongAnswerId() {
		return wrongAnswerId;
	}
	public void setWrongAnswerId(Integer wrongAnswerId) {
		this.wrongAnswerId = wrongAnswerId;
	}
	@Override
	public String toString() {
		return "UsersAnswer [id=" + id + ", userId=" + userId + ", questionId=" + questionId + ", wrongAnswerId="
				+ wrongAnswerId + "]";
	}
	
}
