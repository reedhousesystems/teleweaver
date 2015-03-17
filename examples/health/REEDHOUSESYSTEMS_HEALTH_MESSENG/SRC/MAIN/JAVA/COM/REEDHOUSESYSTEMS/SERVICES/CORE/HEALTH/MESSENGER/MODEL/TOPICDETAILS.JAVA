package com.reedhousesystems.services.core.health.messenger.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

import com.reedhousesystems.services.core.health.messenger.dto.TopicDetailsDTO;

@Entity
@Table(name="TOPIC_DETAILS_TBL")
public class TopicDetails  extends BaseEntity {
	
	@Column(name="section_name")
	private String sectionName;
	
	@Column(name="section_description")
	private String sectionDescription;
	
	@JsonBackReference
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="topic_id")
	private Topic topic;

	public TopicDetails() {}

	public TopicDetails(UUID uuid) {
		super(uuid);
	}
	
	public TopicDetails(TopicDetailsDTO request) {
		this(UUID.fromString(request.getId()));
		this.sectionName = request.getSectionName();
		this.sectionDescription = request.getSectionDescription();
	}
	
	public TopicDetails(Topic topic, TopicDetailsDTO request) {
		this(UUID.fromString(request.getId()));
		this.sectionName = request.getSectionName();
		this.sectionDescription = request.getSectionDescription();
		this.topic = topic;
	}
	
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	public String getSectionName() {
		return sectionName;
	}
	
	public void setSectionDescription(String sectionDescription) {
		this.sectionDescription = sectionDescription;
	}
	
	public String getSectionDescription() {
		return sectionDescription;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
}