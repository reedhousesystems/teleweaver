package com.reedhousesystems.services.core.health.messenger.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.Event;

public class EventDTOLite {
	
	private String id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String description;
	
	@NotNull
	private Date startDate;
	
	@NotNull
	private Date endDate;
	
	@NotNull
	private String status;
	
	public EventDTOLite() {
		
	}
	
	public EventDTOLite(Event event, String status) {
		setId(event.getUuid().toString());
		setName(event.getName());
		setDescription(event.getDescription());
		setStartDate(event.getStartDate());
		setEndDate(event.getEndDate());
		this.status = status;
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
}