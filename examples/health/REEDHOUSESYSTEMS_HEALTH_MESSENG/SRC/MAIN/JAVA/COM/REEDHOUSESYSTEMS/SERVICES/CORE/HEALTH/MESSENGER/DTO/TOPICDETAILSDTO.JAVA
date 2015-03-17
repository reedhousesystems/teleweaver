package com.reedhousesystems.services.core.health.messenger.dto;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.TopicDetails;

public class TopicDetailsDTO {
	
	private String id;
	
	@NotNull
	private String sectionName;
	
	@NotNull
	private String sectionDescription;
	
	public TopicDetailsDTO() {
		
	}
	
	public TopicDetailsDTO(TopicDetails topicDetails) {
		this.id = topicDetails.getUuid().toString();
		this.sectionName = topicDetails.getSectionName();
		this.sectionDescription = topicDetails.getSectionDescription();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getSectionDescription() {
		return sectionDescription;
	}

	public void setSectionDescription(String sectionDescription) {
		this.sectionDescription = sectionDescription;
	}
}