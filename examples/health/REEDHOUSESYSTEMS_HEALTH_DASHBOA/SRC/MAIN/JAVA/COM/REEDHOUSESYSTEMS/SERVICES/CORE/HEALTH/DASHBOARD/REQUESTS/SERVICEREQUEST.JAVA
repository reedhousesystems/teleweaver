package com.reedhousesystems.services.core.health.dashboard.requests;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.dashboard.model.Services;

public class ServiceRequest {
	
	private String id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String description;
	
	@NotNull
	private String type;
	
	public ServiceRequest() {
		
	}
	
	public ServiceRequest(Services service) {
		this.id = service.getUuid().toString();
		this.name = service.getName();
		this.description = service.getDescription();
		this.type = service.getType();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}