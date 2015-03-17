package com.reedhousesystems.services.core.health.messenger.request;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.Message;

public class MessageRequest {
	
	private String id;
	
	@NotNull
	private String item;
	
	@NotNull
	private String description;
	
	public MessageRequest() {
		
	}
	
	public MessageRequest(Message message) {
		setId(message.getUuid().toString());
		setItem(message.getItem());
		setDescription(message.getDescription());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}