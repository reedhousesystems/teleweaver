package com.reedhousesystems.services.core.health.messenger.request;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.Event;
import com.reedhousesystems.services.core.health.messenger.model.Target;

public class EventRequest {
	
	private String id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String description;
	
	@NotNull
	private Date startDate;
	
	@NotNull
	private Date endDate;
	
	private String status;
	
	@Valid
	private Target target;
	
	public EventRequest() {
		
	}
	
	public EventRequest(Event event) {
		setId(event.getUuid().toString());
		setName(event.getName());
		setDescription(event.getDescription());
		setStartDate(event.getStartDate());
		setEndDate(event.getEndDate());
		setStatus(event.getStatus());
		setTarget(event.getTarget());
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

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}
	
}