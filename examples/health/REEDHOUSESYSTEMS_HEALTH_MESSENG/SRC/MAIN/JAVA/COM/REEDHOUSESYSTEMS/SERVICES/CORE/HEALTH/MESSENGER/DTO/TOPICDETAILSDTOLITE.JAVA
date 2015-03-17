package com.reedhousesystems.services.core.health.messenger.dto;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.TopicDetails;

public class TopicDetailsDTOLite {
		
	@NotNull
	private String sectionName;
	
	@NotNull
	private String sectionDescription;
	
	public TopicDetailsDTOLite() {
		
	}
	
	public TopicDetailsDTOLite(TopicDetails topicDetails) {
		this.sectionName = topicDetails.getSectionName();
		this.sectionDescription = topicDetails.getSectionDescription();
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