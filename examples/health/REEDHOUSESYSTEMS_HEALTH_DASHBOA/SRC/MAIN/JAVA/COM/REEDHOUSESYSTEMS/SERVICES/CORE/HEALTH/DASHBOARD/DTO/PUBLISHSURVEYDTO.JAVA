package com.reedhousesystems.services.core.health.dashboard.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.dashboard.model.Question;
import com.reedhousesystems.services.core.health.dashboard.model.Survey;
import com.reedhousesystems.services.core.health.dashboard.requests.QuestionRequest;

public class PublishSurveyDTO {
	
	@NotNull
	private String id;
	
	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	@NotNull
	private Date startDate;
	
	@NotNull
	private Date endDate;
	
	@NotNull
	private String organisation;
	
	private TargetDTO targetDTO;
	
	private List<QuestionRequest> questions = new ArrayList<QuestionRequest>();
	
	public PublishSurveyDTO() {
		
	}
	
	public PublishSurveyDTO(Survey survey) {
		this.id = survey.getUuid().toString();
		this.title = survey.getTitle();
		this.description = survey.getDescription();
		this.startDate = survey.getStartDate();
		this.endDate = survey.getEndDate();
		this.organisation = survey.getOrganisation().getUuid().toString();
		this.setTargetDTO(new TargetDTO(survey.getTarget()));
		for (Question question : survey.getQuestions()) {
			questions.add(new QuestionRequest(question));
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public TargetDTO getTargetDTO() {
		return targetDTO;
	}

	public void setTargetDTO(TargetDTO targetDTO) {
		this.targetDTO = targetDTO;
	}

	public List<QuestionRequest> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionRequest> questions) {
		this.questions = questions;
	}
}