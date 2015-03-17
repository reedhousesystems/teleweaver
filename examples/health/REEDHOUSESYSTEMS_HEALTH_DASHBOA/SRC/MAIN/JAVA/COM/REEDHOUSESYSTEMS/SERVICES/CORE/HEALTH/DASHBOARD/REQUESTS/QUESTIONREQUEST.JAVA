package com.reedhousesystems.services.core.health.dashboard.requests;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.dashboard.model.AnswerOptions;
import com.reedhousesystems.services.core.health.dashboard.model.Question;

public class QuestionRequest {
	
	private String id;
	
	@NotNull
	private String question;
	
	@NotNull
	private String questionType;
	
	@NotNull
	private List<AnswerOptionsRequest> answerOptions = new ArrayList<AnswerOptionsRequest>();
	
	public QuestionRequest() {
		
	}
	
	public QuestionRequest(Question question) {
		this.id = question.getUuid().toString();
		this.question = question.getQuestion();
		this.questionType = question.getQuestionType();
		for (AnswerOptions answer : question.getAnswerOptions()) {
			AnswerOptionsRequest answerOptionsRequest = new AnswerOptionsRequest(answer);
			this.answerOptions.add(answerOptionsRequest);
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public List<AnswerOptionsRequest> getAnswerOptions() {
		return answerOptions;
	}

	public void setAnswerOptions(List<AnswerOptionsRequest> answers) {
		this.answerOptions = answers;
	}
		
}