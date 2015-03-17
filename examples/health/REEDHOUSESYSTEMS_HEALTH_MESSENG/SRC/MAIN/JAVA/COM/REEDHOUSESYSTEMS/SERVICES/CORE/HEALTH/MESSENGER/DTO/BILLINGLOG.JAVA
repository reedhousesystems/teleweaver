package com.reedhousesystems.services.core.health.messenger.dto;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.Demographic;
import com.reedhousesystems.services.core.health.messenger.model.MessageLog;

public class BillingLog {
	
	@NotNull
	private String id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String type;
	
	@NotNull
	private String organisation;

	private Demographic demographic;

	private MessageLog messageLog;
	
	public BillingLog(LogDTO logDTO, Demographic demographic, MessageLog messageLog) {
		this.id = logDTO.getId();
		this.name = logDTO.getName();
		this.type = logDTO.getType();
		this.organisation = logDTO.getOrganisationId();
		this.demographic = demographic;
		this.messageLog = messageLog;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public Demographic getDemographic() {
		return demographic;
	}

	public void setDemographic(Demographic demographic) {
		this.demographic = demographic;
	}

	public MessageLog getMessageLog() {
		return messageLog;
	}

	public void setMessageLog(MessageLog messageLog) {
		this.messageLog = messageLog;
	}	
}