package javaQuiz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="wrong_answer")
public class WrongAnswer {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int questionId;
	private String text;
	public WrongAnswer(int id, int question_id, String text) {
		super();
		this.id = id;
		this.questionId = question_id;
		this.text = text;
	}
	public WrongAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "WrongAnswer [id=" + id + ", questionId=" + questionId + ", text=" + text + "]";
	}

}