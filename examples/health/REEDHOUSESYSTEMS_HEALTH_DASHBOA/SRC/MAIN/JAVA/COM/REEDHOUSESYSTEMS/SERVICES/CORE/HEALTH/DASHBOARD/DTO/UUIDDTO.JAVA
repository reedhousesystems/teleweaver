package com.reedhousesystems.services.core.health.dashboard.dto;

import javax.validation.constraints.NotNull;

public class UUIDDTO {
	
	@NotNull
	private String id;
	
	public UUIDDTO() {}
	
	public UUIDDTO(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	
	
}