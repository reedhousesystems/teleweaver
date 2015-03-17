package com.reedhousesystems.services.core.health.messenger.dto;

import java.util.Date;

import com.reedhousesystems.services.core.health.messenger.model.Event;
import com.reedhousesystems.services.core.health.messenger.model.Location;
import com.reedhousesystems.services.core.health.messenger.model.User;

public class EventRegistrationDTO {
	
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private Date dateOfBirth;
	
	private String gender;
	
	private String occupation;
	
	private String maritalStatus;
	
	private String sexOrientation;
	
	private String language;
	
	private Location location;
	
	public EventRegistrationDTO() {
		
	}
	
	public EventRegistrationDTO(Event event, User user) {
		this.id = event.getUuid().toString();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.dateOfBirth = user.getDemographics().getDateOfBirth();
		this.gender = user.getDemographics().getGender();
		this.occupation = user.getDemographics().getOccupation();
		this.maritalStatus = user.getDemographics().getMaritalStatus();
		this.sexOrientation = user.getDemographics().getSexOrientation();
		this.language = user.getDemographics().getLanguage();
		this.location = user.getDemographics().getLocation();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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