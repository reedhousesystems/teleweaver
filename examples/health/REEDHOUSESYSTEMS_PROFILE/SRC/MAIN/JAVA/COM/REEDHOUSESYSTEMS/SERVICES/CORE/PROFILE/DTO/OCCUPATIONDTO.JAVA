package com.reedhousesystems.services.core.profile.dto;

import com.reedhousesystems.services.core.profile.model.Occupation;

public class OccupationDTO {
	
	private String name;
	
	public OccupationDTO() {}
	
	public OccupationDTO(Occupation occupation) {
		this.name = occupation.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}