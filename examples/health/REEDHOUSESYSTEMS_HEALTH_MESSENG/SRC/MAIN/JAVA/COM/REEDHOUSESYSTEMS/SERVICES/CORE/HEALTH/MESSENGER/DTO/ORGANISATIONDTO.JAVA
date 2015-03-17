package com.reedhousesystems.services.core.health.messenger.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import com.reedhousesystems.services.core.health.messenger.model.Organisation;

public class OrganisationDTO {
	
	@NotNull
	private String id;
	
	@NotNull
	private String name;
	
	private String description;
	
	private String addressLine1;
	
	private String addressLine2;
	
	private String addressLine3;
	
	private String phoneNumber;
	
	@Email
	private String emailAddress;
	
	public OrganisationDTO() {}
	
	public OrganisationDTO(Organisation organisation) {
		this.id = organisation.getUuid().toString();
		this.name = organisation.getName();
		this.description = organisation.getDescription();
		this.addressLine1 = organisation.getAddressLine1();
		this.addressLine2 = organisation.getAddressLine2();
		this.addressLine3 = organisation.getAddressLine3();
		this.phoneNumber = organisation.getPhoneNumber();
		this.emailAddress = organisation.getEmailAddress();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
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