package com.reedhousesystems.services.core.health.dashboard.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

import com.reedhousesystems.services.core.health.dashboard.requests.AnswerOptionsRequest;

@Entity
@Table(name="ANSWER_OPTIONS_TBL")
public class AnswerOptions extends BaseEntity {
	
	@Column(name="answer_option")
	private String answerOption;
	
	@JsonBackReference("question-answer")
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="question_id")
	private Question question;
	
	public AnswerOptions() {
		this(UUID.randomUUID());
	}
	
	public AnswerOptions(UUID uuid) {
		super(uuid);
	}
	
	public AnswerOptions(AnswerOptionsRequest answer) {
		this();
		this.answerOption = answer.getAnswerOption();
	}
	
	public AnswerOptions(String id, AnswerOptionsRequest answer) {
		this(UUID.fromString(id));
		this.answerOption = answer.getAnswerOption();
	}
	
	public AnswerOptions(Question question, AnswerOptionsRequest answer) {
		this();
		this.answerOption = answer.getAnswerOption();
		this.question = question;
	}

	public String getAnswerOption() {
		return answerOption;
	}

	public void setAnswerOption(String answerOption) {
		this.answerOption = answerOption;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
}