package com.reedhousesystems.services.core.health.dashboard.dto;

import javax.validation.constraints.NotNull;

public class ServiceDTO {
	@NotNull
	private String id;
	
	public ServiceDTO() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}