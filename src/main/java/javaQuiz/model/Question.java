package javaQuiz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "question")
public class Question {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)// AUTO
	private int id;
	private String text;
	private String trueAnswer;
	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Question(int id, String text, String trueAnswer) {
		super();
		this.id = id;
		this.text = text;
		this.trueAnswer = trueAnswer;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTrueAnswer() {
		return trueAnswer;
	}
	public void setTrueAnswer(String trueAnswer) {
		this.trueAnswer = trueAnswer;
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", text=" + text + ", trueAnswer=" + trueAnswer + "]";
	}
	
}
