package com.reedhousesystems.services.core.health.dashboard.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.reedhousesystems.services.core.health.dashboard.requests.ServiceRequest;

@Entity
@Table(name="SERVICE_TBL")
public class Services  extends BaseEntity {
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private String type;
	
	@Column
	private String status;
	
	/*@ManyToMany(mappedBy="services")
	private List<Facility> facilities = new ArrayList<Facility>();*/

	public Services() {
		this(UUID.randomUUID());
	}

	public Services(UUID uuid) {
		super(uuid);
	}
	
	public Services(ServiceRequest request) {
		this();
		setName(request.getName());
		setDescription(request.getDescription());
		setType(request.getType());
	}
	
	public Services(String uuid, ServiceRequest request) {
		this(UUID.fromString(uuid));
		setName(request.getName());
		setDescription(request.getDescription());
		setType(request.getType());
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
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}