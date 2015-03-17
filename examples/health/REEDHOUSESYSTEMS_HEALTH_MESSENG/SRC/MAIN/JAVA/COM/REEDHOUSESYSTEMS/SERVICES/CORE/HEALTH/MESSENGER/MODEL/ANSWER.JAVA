package com.reedhousesystems.services.core.health.messenger.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;


@Entity
@Table(name="ANSWER_TBL")
public class Answer extends BaseEntity {
	
	@Column(name="answer")
	private String answer;
	
	@JsonBackReference("question")
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="question_id")
	private Question question;

	@JsonBackReference("answer")
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}