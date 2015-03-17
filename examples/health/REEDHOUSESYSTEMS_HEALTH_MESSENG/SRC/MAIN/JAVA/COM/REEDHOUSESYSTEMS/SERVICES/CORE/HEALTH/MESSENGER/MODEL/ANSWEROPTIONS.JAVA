package com.reedhousesystems.services.core.health.messenger.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

import com.reedhousesystems.services.core.health.messenger.dto.AnswerOptionDTO;
import com.reedhousesystems.services.core.health.messenger.request.AnswerOptionsRequest;

@Entity
@Table(name="ANSWER_OPTIONS_TBL")
public class AnswerOptions  extends BaseEntity {

	@Column(name="answer_option")	
	private String answerOption;
	
	@JsonBackReference("question-option")
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="question_id")	
	private Question question;
	
	public AnswerOptions() {
		
	}
	
	public AnswerOptions(UUID uuid) {
		super(uuid);
	}
	
	public AnswerOptions(AnswerOptionsRequest answer) {
		this(UUID.fromString(answer.getId()));
		this.answerOption = answer.getAnswerOption();
	}
	
	public AnswerOptions(Question question, AnswerOptionDTO answerOptionDTO) {
		this(UUID.fromString(answerOptionDTO.getId()));
		this.answerOption = answerOptionDTO.getAnswerOption();
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