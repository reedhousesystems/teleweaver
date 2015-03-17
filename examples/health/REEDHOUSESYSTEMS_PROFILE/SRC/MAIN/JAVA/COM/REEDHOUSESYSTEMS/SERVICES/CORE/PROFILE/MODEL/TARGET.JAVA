package com.reedhousesystems.services.core.profile.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Target {
	
	@Column(name="dob_from")
	private Date dateOfBirthFrom;
	
	@Column(name="dob_to")
	private Date dateOfBirthTo;
	
	@Column
	private String gender;
	
	@Column
	private String occupation;
	
	@Column(name="marital_status")
	private String maritalStatus;
	
	@Column(name="sex_orientation")
	private String sexOrientation;
	
	@Embedded
	private Address address;
	
	public Target() {
		
	}
	
	public void setDateOfBirthFrom(Date dateOfBirthFrom) {
		this.dateOfBirthFrom = dateOfBirthFrom;
	}
	
	public Date getDateOfBirthFrom() {
		return dateOfBirthFrom;
	}
	
	public void setDateOfBirthTo(Date dateOfBirthTo) {
		this.dateOfBirthTo = dateOfBirthTo;
	}
	
	public Date getDateOfBirthTo() {
		return dateOfBirthTo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	public String getOccupation() {
		return occupation;
	}
	
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public String getMaritalStatus() {
		return maritalStatus;
	}
	
	public void setSexOrientation(String sexOrientation) {
		this.sexOrientation = sexOrientation;
	}
	
	public String getSexOrientation() {
		return sexOrientation;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
}