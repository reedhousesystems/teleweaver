package com.reedhousesystems.services.core.health.messenger.request;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.Occupation;

public class OccupationRequest {
	
	private String id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String description;
	
	public OccupationRequest() {
		
	}
	
	public OccupationRequest(Occupation occupation) {
		setId(occupation.getUuid().toString());
		setName(occupation.getName());
		setDescription(occupation.getDescription());
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