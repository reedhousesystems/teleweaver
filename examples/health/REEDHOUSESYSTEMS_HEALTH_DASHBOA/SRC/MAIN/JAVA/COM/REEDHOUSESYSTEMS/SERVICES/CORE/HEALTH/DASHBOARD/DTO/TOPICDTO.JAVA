package com.reedhousesystems.services.core.health.dashboard.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.dashboard.model.Topic;
import com.reedhousesystems.services.core.health.dashboard.model.TopicDetails;

public class TopicDTO {
	
	@NotNull
	private String id;
	
	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	@NotNull
	private String language;
	
	@NotNull
	private String status;
	
	private List<TopicDetailsDTO> topicDetailsDTOList = new ArrayList<TopicDetailsDTO>();
	
	public TopicDTO() {
		
	}
	
	public TopicDTO(Topic topic) {
		this.id = topic.getUuid().toString();
		this.title = topic.getTitle();
		this.description = topic.getDescription();
		this.language = topic.getLanguage();
		this.status = topic.getStatus();
		for (TopicDetails topicDetails : topic.getTopicDetails()) {
			topicDetailsDTOList.add(new TopicDetailsDTO(topicDetails));
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<TopicDetailsDTO> getTopicDetailsDTOList() {
		return topicDetailsDTOList;
	}

	public void setTopicDetailsDTOList(List<TopicDetailsDTO> topicDetailsDTOList) {
		this.topicDetailsDTOList = topicDetailsDTOList;
	}
	
}