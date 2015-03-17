package com.reedhousesystems.services.core.health.dashboard.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.reedhousesystems.services.core.health.dashboard.requests.ServiceTypeRequest;

@Entity
@Table(name="SERVICE_TYPE_TBL")
public class ServiceType  extends BaseEntity {
	
	@Column
	
	private String name;
	@Column
	private String description;

	public ServiceType() {
		this(UUID.randomUUID());
	}

	public ServiceType(UUID uuid) {
		super(uuid);
	}
	
	public ServiceType(ServiceTypeRequest request) {
		this();
		setName(request.getName());
		setDescription(request.getDescription());
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}