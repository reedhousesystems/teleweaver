package com.reedhousesystems.services.core.health.messenger.model;

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

import com.reedhousesystems.services.core.health.messenger.dto.AnswerOptionDTO;
import com.reedhousesystems.services.core.health.messenger.dto.QuestionDTO;
import com.reedhousesystems.services.core.health.messenger.request.AnswerOptionsRequest;
import com.reedhousesystems.services.core.health.messenger.request.QuestionRequest;

@Entity
@Table(name="QUESTION_TBL")
public class Question extends BaseEntity {
	
	@Column
	private String question;
	
	@Column(name="question_type")
	private String questionType;
	
	@JsonBackReference("survey")
	@ManyToOne(optional = false)
	@JoinColumn(name="survey_id")
	private Survey survey;
	
	@JsonManagedReference("answerOptions")
	@OneToMany(mappedBy="question", cascade=CascadeType.ALL)
	private List<AnswerOptions> answerOptions = new ArrayList<AnswerOptions>();
	
	public Question() {
		
	}
	
	public Question(UUID uuid) {
		super(uuid);
	}
	
	public Question(QuestionRequest questionRequest) {
		this(UUID.fromString(questionRequest.getId()));
		this.question = questionRequest.getQuestion();
		this.questionType = questionRequest.getQuestionType();
		for (AnswerOptionsRequest answer : questionRequest.getAnswerOptions()) {
			answerOptions.add(new AnswerOptions(answer));
		}
	}
	
	public Question(Survey survey, QuestionDTO questionDTO) {
		this(UUID.fromString(questionDTO.getId()));
		this.question = questionDTO.getQuestion();
		this.questionType = questionDTO.getQuestionType();
		this.survey = survey;
		for (AnswerOptionDTO answerOptionDTO: questionDTO.getAnswerOptions()) {
			answerOptions.add(new AnswerOptions(this, answerOptionDTO));
		}
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