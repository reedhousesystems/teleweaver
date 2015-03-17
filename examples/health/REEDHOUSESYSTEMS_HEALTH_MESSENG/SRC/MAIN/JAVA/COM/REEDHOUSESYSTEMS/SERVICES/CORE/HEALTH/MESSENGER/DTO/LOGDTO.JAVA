package com.reedhousesystems.services.core.health.messenger.dto;

import java.util.Date;

import com.reedhousesystems.services.core.health.messenger.model.Log;
import com.reedhousesystems.services.core.health.messenger.model.Target;

public class LogDTO {
	
	private String id;
	
	private String name;
	
	private String type;
	
	private String organisationId;
	
	private Date startDate;

	private Date endDate;
	
	private Target target;
	
	public LogDTO() {
		
	}
	
	public LogDTO(Log log) {
		this.id = log.getIdentifier();
		this.name = log.getName();
		this.type = log.getType();
		this.organisationId = log.getOrganisationId();
		this.startDate = log.getStartDate();
		this.endDate = log.getEndDate();
		this.target = log.getTarget();
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

	public String getOrganisationId() {
		return organisationId;
	}

	public void setOrganisationId(String organisationId) {
		this.organisationId = organisationId;
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

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}
}