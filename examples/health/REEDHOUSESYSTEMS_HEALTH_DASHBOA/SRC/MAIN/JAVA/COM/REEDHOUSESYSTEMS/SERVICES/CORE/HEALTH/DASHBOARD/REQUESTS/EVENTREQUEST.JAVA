package com.reedhousesystems.services.core.health.dashboard.requests;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.dashboard.model.Event;
import com.reedhousesystems.services.core.health.dashboard.model.Target;

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
	
	private String organisation;

	@NotNull
	private String userId;
	
	@Valid
	private Target target;
	
	public EventRequest() {
		
	}
	
	public EventRequest(Event event) {
		this.id = event.getUuid().toString();
		this.name = event.getName();
		this.description = event.getDescription();
		this.startDate = event.getStartDate();
		this.endDate = event.getEndDate();
		this.status = event.getStatus();
		this.organisation = event.getOrganisation().getUuid().toString();
		this.target = event.getTarget();
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

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}
	
}