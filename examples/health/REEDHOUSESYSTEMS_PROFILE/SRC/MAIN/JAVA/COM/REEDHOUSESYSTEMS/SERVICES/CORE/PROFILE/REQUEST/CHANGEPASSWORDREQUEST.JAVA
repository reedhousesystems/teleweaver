package com.reedhousesystems.services.core.profile.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ChangePasswordRequest {
	
	@NotNull
	private String uuid;

	@Length(min=8)
	@NotNull
	private String password;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}