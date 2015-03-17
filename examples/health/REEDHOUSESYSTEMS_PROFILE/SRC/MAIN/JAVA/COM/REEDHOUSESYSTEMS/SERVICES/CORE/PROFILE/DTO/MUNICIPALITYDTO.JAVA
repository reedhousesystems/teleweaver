package com.reedhousesystems.services.core.profile.dto;

import com.reedhousesystems.services.core.profile.model.Location;

public class MunicipalityDTO {
	
	private String municipality;
	
	public MunicipalityDTO() {}
	
	public MunicipalityDTO(Location location) {
		this.municipality = location.getMunicipality();
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	
}