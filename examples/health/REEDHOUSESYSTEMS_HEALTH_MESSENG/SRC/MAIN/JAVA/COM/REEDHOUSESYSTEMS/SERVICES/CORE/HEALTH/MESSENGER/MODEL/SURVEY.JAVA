package com.reedhousesystems.services.core.health.messenger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.reedhousesystems.services.core.health.messenger.dto.QuestionDTO;
import com.reedhousesystems.services.core.health.messenger.dto.SurveyDTO;
import com.reedhousesystems.services.core.health.messenger.request.QuestionRequest;
import com.reedhousesystems.services.core.health.messenger.request.SurveyRequest;

@Entity
@Table(name="SURVEY_TBL")
public class Survey extends BaseEntity {
	
	@Column
	private String title;
	
	@Column
	private String description;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@JsonBackReference("organisation")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="organisation_id")
	private Organisation organisation;

	@JsonBackReference("target")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="target_id")
	private Target target;
	
	@JsonManagedReference("survey-question")
	@OneToMany(fetch = FetchType.EAGER, mappedBy="survey", cascade=CascadeType.ALL, orphanRemoval=true)	
	private List<Question> questions = new ArrayList<Question>();
	
	public Survey() {
		
	}
	
	public Survey(UUID uuid) {
		super(uuid);
	}
	
	public Survey(SurveyRequest surveyRequest) {
		this(UUID.fromString(surveyRequest.getId()));
		this.title = surveyRequest.getTitle();
		this.description = surveyRequest.getDescription();
		this.startDate = surveyRequest.getStartDate();
		this.endDate = surveyRequest.getEndDate();
		for (QuestionRequest question : surveyRequest.getQuestions()) {
			questions.add(new Question(question));
		}
	}
	
	public Survey(SurveyDTO survey) {
		this(UUID.fromString(survey.getId()));
		this.title = survey.getTitle();
		this.description = survey.getDescription();
		this.startDate = survey.getStartDate();
		this.endDate = survey.getEndDate();
		this.target = new Target(survey.getTargetDTO());
		for (QuestionDTO question : survey.getQuestions()) {
			questions.add(new Question(this, question));
		}
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}
}