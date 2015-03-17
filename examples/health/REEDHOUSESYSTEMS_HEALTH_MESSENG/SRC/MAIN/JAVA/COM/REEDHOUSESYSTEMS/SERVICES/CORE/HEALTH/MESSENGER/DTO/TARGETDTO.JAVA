package com.reedhousesystems.services.core.health.messenger.dto;

import java.util.Date;

import com.reedhousesystems.services.core.health.messenger.model.Target;

public class TargetDTO {
	
	private String id;
	
	private String language;
	
	private Date dateOfBirthFrom;
	
	private Date dateOfBirthTo;
	
	private String gender;
	
	private String occupation;
	
	private String maritalStatus;
	
	private String sexOrientation;
	
	private LocationDTO locationDTO;
	
	public TargetDTO() {
		
	}
	
	public TargetDTO(Target target) {
		this.id = target.getUuid().toString();
		this.language = target.getLanguage();
		this.dateOfBirthFrom = target.getDateOfBirthFrom();
		this.dateOfBirthTo = target.getDateOfBirthTo();
		this.gender = target.getGender();
		this.occupation = target.getOccupation();
		this.maritalStatus = target.getMaritalStatus();
		this.sexOrientation = target.getSexOrientation();
		this.setLocationDTO(new LocationDTO(target.getLocation()));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getDateOfBirthFrom() {
		return dateOfBirthFrom;
	}

	public void setDateOfBirthFrom(Date dateOfBirthFrom) {
		this.dateOfBirthFrom = dateOfBirthFrom;
	}

	public Date getDateOfBirthTo() {
		return dateOfBirthTo;
	}

	public void setDateOfBirthTo(Date dateOfBirthTo) {
		this.dateOfBirthTo = dateOfBirthTo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getSexOrientation() {
		return sexOrientation;
	}

	public void setSexOrientation(String sexOrientation) {
		this.sexOrientation = sexOrientation;
	}

	public LocationDTO getLocationDTO() {
		return locationDTO;
	}

	public void setLocationDTO(LocationDTO locationDTO) {
		this.locationDTO = locationDTO;
	}
}