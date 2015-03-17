package com.reedhousesystems.services.core.profile.dto;

import com.reedhousesystems.services.core.profile.model.Location;

public class VillageDTO {
	
	private String village;
	
	public VillageDTO() {}
	
	public VillageDTO(Location location) {
		this.village = location.getVillage();
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}
	
}