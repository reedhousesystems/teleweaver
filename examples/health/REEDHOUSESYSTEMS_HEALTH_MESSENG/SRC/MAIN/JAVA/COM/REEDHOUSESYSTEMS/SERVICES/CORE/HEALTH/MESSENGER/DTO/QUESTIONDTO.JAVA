package com.reedhousesystems.services.core.health.messenger.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.AnswerOptions;
import com.reedhousesystems.services.core.health.messenger.model.Question;

public class QuestionDTO {

	private String id;
	
	@NotNull
	private String question;
	
	@NotNull
	private String questionType;
	
	private List<AnswerOptionDTO> answerOptions = new ArrayList<AnswerOptionDTO>();
	
	public QuestionDTO() {
		
	}
	
	public QuestionDTO(Question question) {
		this.id = question.getUuid().toString();
		this.question = question.getQuestion();
		this.questionType = question.getQuestionType();
		for (AnswerOptions answerOption : question.getAnswerOptions()) {
			answerOptions.add(new AnswerOptionDTO(answerOption));
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

	public List<AnswerOptionDTO> getAnswerOptions() {
		return answerOptions;
	}

	public void setAnswerOptions(List<AnswerOptionDTO> answerOptions) {
		this.answerOptions = answerOptions;
	}
}