package com.reedhousesystems.services.core.health.messenger.request;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginAttemptRequest {
	
	@NotNull
	private int loginAttemps;
	
	public LoginAttemptRequest() {
		
	}
	
	public LoginAttemptRequest(final int loginAttemps) {
		this.setLoginAttemps(loginAttemps);
	}

	public int getLoginAttemps() {
		return loginAttemps;
	}

	public void setLoginAttemps(int loginAttemps) {
		this.loginAttemps = loginAttemps;
	}
	
}