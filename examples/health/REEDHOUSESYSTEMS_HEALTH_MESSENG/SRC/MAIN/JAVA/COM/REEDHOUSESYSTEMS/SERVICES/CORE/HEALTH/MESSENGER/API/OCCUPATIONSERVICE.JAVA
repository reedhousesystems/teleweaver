package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.dto.OccupationDTO;
import com.reedhousesystems.services.core.health.messenger.request.OccupationRequest;

public interface OccupationService {
	
	public void saveOccupation(OccupationRequest request) throws Exception;
	
	public List<OccupationDTO> getAllOccupations() throws Exception;
	
}