package com.reedhousesystems.services.core.health.messenger.dto;

import com.reedhousesystems.services.core.health.messenger.model.Location;

public class LocationDTO {
	
	private String id;
	
	private String province;
	
	private String district;
	
	private String municipality;
	
	private String village;
	
	public LocationDTO() {
		
	}
	
	public LocationDTO(Location location) {
		this.setId(location.getUuid().toString());
		this.province = location.getProvince();
		this.district = location.getDistrict();
		this.municipality = location.getMunicipality();
		this.village = location.getVillage();
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