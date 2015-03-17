package com.reedhousesystems.services.core.health.messenger.request;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.Service;


public class ServiceRequest {
	
	private String id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String description;
	
	@NotNull
	private String type;
	
	@NotNull
	private String status;
	
	public ServiceRequest() {}
	
	public ServiceRequest(Service service) {
		this.id = service.getUuid().toString();
		this.name = service.getName();
		this.description = service.getDescription();
		this.type = service.getType();
		this.status = service.getStatus();
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}