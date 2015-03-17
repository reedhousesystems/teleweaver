package com.reedhousesystems.services.core.health.messenger.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.Email;


@Embeddable
public class Contact {
	
	@Column(name="email_address")
	@Email
	private String emailAddress;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	public Contact() {
		
	}
	
	public Contact(User user) {
		setPhoneNumber(user.getContact().getPhoneNumber());
		setEmailAddress(user.getContact().getEmailAddress());
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