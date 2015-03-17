package com.reedhousesystems.services.core.health.messenger.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.Question;
import com.reedhousesystems.services.core.health.messenger.model.Survey;

public class SurveyDTOLite {

	private String id;
	
	@NotNull
	private String title;
	
	private List<QuestionDTO> questions = new ArrayList<QuestionDTO>();
	
	public SurveyDTOLite(Survey survey) {
		this.setId(survey.getUuid().toString());
		this.setTitle(survey.getTitle());
		for (Question question : survey.getQuestions()) {
			questions.add(new QuestionDTO(question));
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<QuestionDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}
}