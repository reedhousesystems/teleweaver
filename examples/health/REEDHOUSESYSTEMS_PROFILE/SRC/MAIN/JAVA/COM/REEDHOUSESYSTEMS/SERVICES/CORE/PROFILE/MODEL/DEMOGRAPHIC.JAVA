package com.reedhousesystems.services.core.profile.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.reedhousesystems.services.core.profile.dto.DemographicDTO;

@Entity
@Table(name="DEMOGRAPHIC_TBL")
public class Demographic extends BaseEntity {
	
	@Column(name="date_of_birth")
	private Date dateOfBirth;
	
	@Column
	private String gender;
	
	@Column
	private String occupation;
	
	@Column(name="marital_status")
	private String maritalStatus;	
	
	@Column
	private String sexOrientation;
	
	@Column
	private String language;
	
	@ManyToOne
	@JoinColumn(name="location_id")
	private Location location;
	
	public Demographic() {
		
	}
	
	public Demographic(DemographicDTO demographicDTO) {
		setDateOfBirth(demographicDTO.getDateOfBirth());
		setGender(demographicDTO.getGender());
		setOccupation(demographicDTO.getOccupation());
		setMaritalStatus(demographicDTO.getMaritalStatus());
		setSexOrientation(demographicDTO.getSexOrientation());
		setLanguage(demographicDTO.getLanguage());
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