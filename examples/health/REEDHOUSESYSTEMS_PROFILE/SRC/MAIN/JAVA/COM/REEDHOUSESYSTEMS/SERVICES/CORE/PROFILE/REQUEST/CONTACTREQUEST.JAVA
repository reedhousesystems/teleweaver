package com.reedhousesystems.services.core.profile.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

public class ContactRequest {
	

    @Email
    @NotNull
    private String emailAddress;
	private String phoneNumber;
	
	public ContactRequest() {
		
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}