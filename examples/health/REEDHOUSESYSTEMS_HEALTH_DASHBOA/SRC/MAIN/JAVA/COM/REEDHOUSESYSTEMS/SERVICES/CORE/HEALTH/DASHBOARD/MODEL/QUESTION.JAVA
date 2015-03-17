package com.reedhousesystems.services.core.health.dashboard.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import com.reedhousesystems.services.core.health.dashboard.requests.AnswerOptionsRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.QuestionRequest;

@Entity
@Table(name="QUESTION_TBL")
public class Question  extends BaseEntity {
	
	@Column
	private String question;
	
	@Column(name="question_type")
	private String questionType;
	
	@JsonBackReference("survey-question")
	@ManyToOne(optional = false)
	@JoinColumn(name="survey_id")
	private Survey survey;
	
	@JsonManagedReference("question-answer")
	@OneToMany(mappedBy="question", cascade=CascadeType.ALL)
	private List<AnswerOptions> answerOptions = new ArrayList<AnswerOptions>();
	
	public Question() {
		this(UUID.randomUUID());
	}

	public Question(UUID uuid) {
		super(uuid);
	}

	public Question(QuestionRequest request) {
		this();
		this.question = request.getQuestion();
		this.questionType = request.getQuestionType();
		for (AnswerOptionsRequest answer : request.getAnswerOptions()) {
			AnswerOptions options = new AnswerOptions(answer);
			this.answerOptions.add(options);
		}
	}

	public Question(Survey survey, QuestionRequest request) {
		this();
		this.question = request.getQuestion();
		this.questionType = request.getQuestionType();
		for (AnswerOptionsRequest answer : request.getAnswerOptions()) {
			AnswerOptions options = new AnswerOptions(this, answer);
			this.answerOptions.add(options);
		}
		this.survey = survey;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestionType(String questionType) {
		this.questionType = questionType;		
	}
	
	public String getQuestionType() {
		return questionType;
	}
	
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	
	public Survey getSurvey() {
		return survey;
	}
	
	public List<AnswerOptions> getAnswerOptions() {
		return answerOptions;
	}
	
	public void setAnswerOptions(List<AnswerOptions> answerOptions) {
		this.answerOptions = answerOptions;
	}
	
}