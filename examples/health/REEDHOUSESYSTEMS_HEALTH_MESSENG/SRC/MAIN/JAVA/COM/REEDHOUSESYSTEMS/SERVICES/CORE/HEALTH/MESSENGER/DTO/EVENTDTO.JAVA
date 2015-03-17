package com.reedhousesystems.services.core.health.messenger.dto;

import java.util.Date;

import com.reedhousesystems.services.core.health.messenger.model.Event;

public class EventDTO {
	
	private String id;
	
	private String name;
	
	private String description;
	
	private Date creationDate;
	
	private Date startDate;
	
	private Date endDate;
	
	private String status;
	
	private String organisation;
	
	private TargetDTO targetDTO;
	
	public EventDTO() {
		
	}
	
	public EventDTO(Event event) {
		this.id = event.getUuid().toString();
		this.name = event.getName();
		this.description = event.getDescription();
		this.creationDate = event.getCreationDate();
		this.startDate = event.getStartDate();
		this.endDate = event.getEndDate();
		this.organisation = event.getOrganisation().getUuid().toString();
		this.targetDTO = new TargetDTO(event.getTarget());
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public TargetDTO getTargetDTO() {
		return targetDTO;
	}

	public void setTargetDTO(TargetDTO targetDTO) {
		this.targetDTO = targetDTO;
	}
}