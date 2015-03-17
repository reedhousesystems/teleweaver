package com.reedhousesystems.services.core.health.dashboard.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.reedhousesystems.services.core.health.dashboard.dto.TargetDTO;

@Entity
@Table(name="TARGET_TBL")
public class Target extends BaseEntity implements Serializable {
	
	@Column
	private String language;
	
	@Column(name="dob_from")
	private Date dateOfBirthFrom;
	
	@Column(name="dob_to")
	private Date dateOfBirthTo;
	
	@Column
	private String sex;
	
	@Column
	private String occupation;
	
	@Column(name="marital_status")
	private String maritalStatus;
	
	@Column(name="sex_orientation")
	private String sexOrientation;
	
	@OneToOne
	@JoinColumn(name="location_id")
	private Location location;
	
	public Target() {
		this(UUID.randomUUID());
	}
	
	public Target(UUID uuid) {
		super(uuid);
	}
	
	public Target(TargetDTO targetDTO) {
		this();
		this.language = targetDTO.getLanguage();
		this.dateOfBirthFrom = targetDTO.getDateOfBirthFrom();
		this.dateOfBirthTo = targetDTO.getDateOfBirthTo();
		this.sex = targetDTO.getSex();
		this.occupation = targetDTO.getOccupation();
		this.maritalStatus = targetDTO.getMaritalStatus();
		this.sexOrientation = targetDTO.getSexOrientation();
		this.location = new Location(targetDTO.getLocationDTO());
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getSex() {
		return sex;
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}