package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.requests.OccupationRequest;

public interface OccupationService {
	
	public void saveOccupation(OccupationRequest request) throws Exception;
	
	public void updateOccupation(String occupationId, OccupationRequest request) throws Exception;
	
	public void deleteOccupation(String occupationId) throws Exception;
	
	public OccupationRequest getOccupationById(String occupationId) throws Exception;
	
	public List<OccupationRequest> getAllOccupations() throws Exception;
	
}