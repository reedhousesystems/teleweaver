package com.reedhousesystems.services.core.health.dashboard.requests;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.dashboard.model.Occupation;

public class OccupationRequest {
	
	private String id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String description;
	
	public OccupationRequest() {
		
	}
	
	public OccupationRequest(Occupation occupation) {
		this.id = occupation.getUuid().toString();
		this.name = occupation.getName();
		this.description = occupation.getDescription();
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
}