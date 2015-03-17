package com.reedhousesystems.services.core.health.messenger.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.reedhousesystems.services.core.health.messenger.request.OccupationRequest;

@Entity
@Table(name="OCCUPATION_TBL")
public class Occupation  extends BaseEntity {
	
	@Column
	private String name;
	
	@Column
	private String description;

	public Occupation() {
		this(UUID.randomUUID());
	}

	public Occupation(UUID uuid) {
		super(uuid);
	}
	
	public Occupation(OccupationRequest request) {
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