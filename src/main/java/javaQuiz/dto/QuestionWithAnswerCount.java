package javaQuiz.dto;

import javaQuiz.model.Question;

public class QuestionWithAnswerCount {
	private Question question;
	private int questionCount;
	private int correctAnswerCount;
	private float percentage;
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public int getQuestionCount() {
		return questionCount;
	}
	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}
	public int getCorrectAnswerCount() {
		return correctAnswerCount;
	}
	public void setCorrectAnswerCount(int correctAnswerCount) {
		this.correctAnswerCount = correctAnswerCount;
	}
	public float getPercentage() {
		return percentage;
	}
	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
	
}
