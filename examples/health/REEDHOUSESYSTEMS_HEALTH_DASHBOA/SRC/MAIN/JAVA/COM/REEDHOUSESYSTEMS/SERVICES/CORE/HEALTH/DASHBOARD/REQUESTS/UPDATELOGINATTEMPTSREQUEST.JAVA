package com.reedhousesystems.services.core.health.dashboard.requests;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UpdateLoginAttemptsRequest {
	
	@NotNull
	private int loginAttemps;
	
	public UpdateLoginAttemptsRequest() {
		
	}
	
	public UpdateLoginAttemptsRequest(final int loginAttemps) {
		this.setLoginAttemps(loginAttemps);
	}

	public int getLoginAttemps() {
		return loginAttemps;
	}

	public void setLoginAttemps(int loginAttemps) {
		this.loginAttemps = loginAttemps;
	}
	
}