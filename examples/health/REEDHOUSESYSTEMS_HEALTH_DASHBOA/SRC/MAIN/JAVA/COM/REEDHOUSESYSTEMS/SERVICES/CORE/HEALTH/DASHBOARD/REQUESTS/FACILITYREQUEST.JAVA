package com.reedhousesystems.services.core.health.dashboard.requests;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.dashboard.model.Address;
import com.reedhousesystems.services.core.health.dashboard.model.Facility;
import com.reedhousesystems.services.core.health.dashboard.model.Services;

public class FacilityRequest {
	
	private String id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String type;
	
	@NotNull
	@Valid
	private Address address;
	
	public FacilityRequest() {
		
	}
	
	public FacilityRequest(Facility facility) {
		setId(facility.getUuid().toString());
		setName(facility.getName());
		setType(facility.getType());
		setAddress(facility.getAddress());
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
}