package com.reedhousesystems.services.core.health.messenger.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="SERVICE_TBL")
public class Service extends BaseEntity {
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private String type;
	
	@Column
	private String status;
	
	public Service() {
		
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