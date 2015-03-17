package com.reedhousesystems.services.core.health.dashboard.requests;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.dashboard.model.Topic;

public class TopicRequest {
	
	private String id;
	
	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	public TopicRequest() {
		
	}
	
	public TopicRequest(Topic topic) {
		setId(topic.getUuid().toString());
		setTitle(topic.getTitle());
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
	
}