package com.reedhousesystems.services.core.health.dashboard.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.dashboard.model.Question;
import com.reedhousesystems.services.core.health.dashboard.model.Survey;
import com.reedhousesystems.services.core.health.dashboard.requests.QuestionRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.SurveyRequest;

public class SurveyDTO {
	
	@NotNull
	private String id;
	
	private List<QuestionRequest> questions = new ArrayList<QuestionRequest>();
	
	public SurveyDTO(Survey survey) {
		this.setId(survey.getUuid().toString());
		for (Question question : survey.getQuestions()) {
			questions.add(new QuestionRequest(question));
		}
	}
	
	public SurveyDTO(SurveyRequest survey) {
		this.setId(survey.getId());
		this.questions = survey.getQuestions();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<QuestionRequest> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionRequest> questions) {
		this.questions = questions;
	}
}