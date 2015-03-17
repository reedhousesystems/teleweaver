package com.reedhousesystems.services.core.health.messenger.request;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.Address;
import com.reedhousesystems.services.core.health.messenger.model.Facility;

public class FacilityRequest {
	
	private String id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String type;
	
	@NotNull
	private Address address;
	
	public FacilityRequest() {}
	
	public FacilityRequest(Facility facility) {
		this.id = facility.getUuid().toString();
		this.name = facility.getName();
		this.type = facility.getType();
		this.address = facility.getAddress();
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