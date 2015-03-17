package com.reedhousesystems.services.core.profile.api;

import java.util.List;

import com.reedhousesystems.services.core.profile.dto.OccupationDTO;

public interface OccupationService {
	
	public List<OccupationDTO> getAllOccupations() throws Exception;
	
}