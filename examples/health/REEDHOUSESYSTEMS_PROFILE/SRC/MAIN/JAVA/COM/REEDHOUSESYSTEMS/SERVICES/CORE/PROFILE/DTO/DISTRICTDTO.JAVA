package com.reedhousesystems.services.core.profile.dto;

import com.reedhousesystems.services.core.profile.model.Location;

public class DistrictDTO {
	
	private String district;
	
	public DistrictDTO() {}
	
	public DistrictDTO(Location location) {
		this.district = location.getDistrict();
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
}