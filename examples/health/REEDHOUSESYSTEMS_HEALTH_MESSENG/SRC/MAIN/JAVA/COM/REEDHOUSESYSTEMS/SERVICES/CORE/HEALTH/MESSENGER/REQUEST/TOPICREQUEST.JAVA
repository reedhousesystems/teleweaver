package com.reedhousesystems.services.core.health.messenger.request;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.Topic;

public class TopicRequest {
	
	@NotNull
	private String id;
	
	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	public TopicRequest() {
		
	}
	
	public TopicRequest(Topic topic) {
		this.setId(topic.getUuid().toString());
		this.setTitle(topic.getTitle());
		this.setDescription(topic.getDescription());
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