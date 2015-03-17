package com.reedhousesystems.services.core.health.messenger.request;

import javax.validation.constraints.NotNull;

import com.reedhousesystems.services.core.health.messenger.model.Location;

public class LocationRequest {
	
	private String id;
	
	@NotNull
	private String province;
	
	@NotNull
	private String district;
	
	@NotNull
	private String municipality;
	
	@NotNull
	private String village;
	
	public LocationRequest() {
		
	}
	
	public LocationRequest(Location location) {
		setId(location.getUuid().toString());
		setProvince(location.getProvince());
		setDistrict(location.getDistrict());
		setMunicipality(location.getMunicipality());
		setVillage(location.getVillage());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}
	
}