package com.reedhousesystems.services.core.health.dashboard.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import com.reedhousesystems.services.core.health.dashboard.dto.OrganisationDTO;

/** 
 * @date 12/09/2014
 * @author Chikumbutso Gremu
 */

@Entity
@Table(name="ORGANIZATION_TBL")
public class Organisation extends BaseEntity implements Serializable {
	
	@NotNull
	@Column
	private String name;

	@Column
	private String description;
	
	@Column(name="address_line_1")
	private String addressLine1;

	@Column(name="address_line_2")
	private String addressLine2;

	@Column(name="address_line_3")
	private String addressLine3;

	@Column(name="phone_number")
	private String phoneNumber;
	
	@Email
	@Column(name="email_address")
	private String emailAddress;
	
	public Organisation() {
		this(UUID.randomUUID());
	}
	
	public Organisation(UUID uuid) {
		super(uuid);
	}
	
	public Organisation(OrganisationDTO organisationDTO) {
		this();
		this.name = organisationDTO.getName();
		this.description = organisationDTO.getDescription();
		this.addressLine1 = organisationDTO.getAddressLine1();
		this.addressLine2 = organisationDTO.getAddressLine2();
		this.addressLine3 = organisationDTO.getAddressLine3();
		this.phoneNumber = organisationDTO.getPhoneNumber();
		this.emailAddress = organisationDTO.getEmailAddress();
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