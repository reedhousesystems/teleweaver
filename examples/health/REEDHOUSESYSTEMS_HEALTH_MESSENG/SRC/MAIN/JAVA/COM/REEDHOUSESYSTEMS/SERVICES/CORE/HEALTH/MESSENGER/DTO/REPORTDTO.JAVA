package com.reedhousesystems.services.core.health.messenger.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class ReportDTO {
	
	@NotNull
	@Column	
	private String id;
	
	public ReportDTO() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}