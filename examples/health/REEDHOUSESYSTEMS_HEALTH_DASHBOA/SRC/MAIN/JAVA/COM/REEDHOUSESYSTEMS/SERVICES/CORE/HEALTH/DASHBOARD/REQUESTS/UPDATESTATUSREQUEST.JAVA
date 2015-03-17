package com.reedhousesystems.services.core.health.dashboard.requests;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UpdateStatusRequest {
	
	@NotNull
	private int status;
	
	public UpdateStatusRequest() {
		
	}
	
	public UpdateStatusRequest(final int status) {
		this.setStatus(status);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}