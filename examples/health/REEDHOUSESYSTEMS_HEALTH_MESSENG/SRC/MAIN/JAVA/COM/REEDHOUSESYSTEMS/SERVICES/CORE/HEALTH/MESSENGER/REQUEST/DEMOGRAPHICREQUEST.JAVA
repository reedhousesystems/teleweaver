package com.reedhousesystems.services.core.health.messenger.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import com.reedhousesystems.services.core.health.messenger.model.Location;

public class DemographicRequest {
	
	@NotNull
	private Date dateOfBirth;
	@NotNull
	private String gender;
	@NotNull
	private String maritalStatus;
	@NotNull
	private String occupation;
	/*@NotNull
	private String province;
	@NotNull
	private String district;
	@NotNull
	private String municipality;
	@NotNull
	private String village;*/
	@NotNull
	private String sexOrientation;
	private String language;
	private Location location;

    @Email
    private String emailAddress;
	
	public DemographicRequest() {
		
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	/*public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getMunicipality() {
		return municipality;
	}
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}*/
	public String getSexOrientation() {
		return sexOrientation;
	}
	public void setSexOrientation(String sexOrientation) {
		this.sexOrientation = sexOrientation;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}