package com.reedhousesystems.services.core.profile.dto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatusDTO {
	
	@NotNull
	private String status;
	
	public StatusDTO() {
		
	}
	
	public StatusDTO(String status) {
		this.setStatus(status);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
}