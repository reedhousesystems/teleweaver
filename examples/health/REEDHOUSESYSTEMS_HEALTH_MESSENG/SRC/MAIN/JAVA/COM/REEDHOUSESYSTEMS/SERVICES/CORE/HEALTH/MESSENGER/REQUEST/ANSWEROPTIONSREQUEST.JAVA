package com.reedhousesystems.services.core.health.messenger.request;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.AnswerOptions;

public class AnswerOptionsRequest {
	
	private String id;
	
	@NotNull
	private String answerOption;
	
	public AnswerOptionsRequest() {}
	
	public AnswerOptionsRequest(AnswerOptions answerOptions) {
		this.id = answerOptions.getUuid().toString();
		this.answerOption = answerOptions.getAnswerOption();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnswerOption() {
		return answerOption;
	}

	public void setAnswerOption(String answerOption) {
		this.answerOption = answerOption;
	}

}