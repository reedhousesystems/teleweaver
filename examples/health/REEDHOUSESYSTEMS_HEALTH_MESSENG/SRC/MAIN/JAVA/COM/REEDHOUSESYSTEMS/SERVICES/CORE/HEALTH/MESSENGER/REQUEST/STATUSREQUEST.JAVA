package com.reedhousesystems.services.core.health.messenger.request;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatusRequest {
	
	@NotNull
	private int status;
	
	public StatusRequest() {
		
	}
	
	public StatusRequest(final int status) {
		this.setStatus(status);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}