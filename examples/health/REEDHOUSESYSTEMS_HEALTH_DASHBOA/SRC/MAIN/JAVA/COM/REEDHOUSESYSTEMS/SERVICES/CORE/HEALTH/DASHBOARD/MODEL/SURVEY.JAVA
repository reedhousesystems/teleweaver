package com.reedhousesystems.services.core.health.dashboard.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import com.reedhousesystems.services.core.health.dashboard.requests.SurveyRequest;

@Entity
@Table(name="SURVEY_TBL")
public class Survey  extends BaseEntity {
	
	@Column
	private String title;
	
	@Column
	private String description;
	
	@Column
	private String status;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@JsonBackReference
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
		this(UUID.randomUUID());
	}
	
	public Survey(UUID uuid) {
		super(uuid);
	}
	
	public Survey(SurveyRequest request) {
		this();
		setTitle(request.getTitle());
		setDescription(request.getDescription());
		setStartDate(request.getStartDate());
		setEndDate(request.getEndDate());
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
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getCreationDate() {
		return creationDate;
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

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
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
	
}