package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.model.Occupation;

public interface OccupationDao {
	
	public void saveOccupation(Occupation occupation) throws Exception;
	
	public Occupation getOccupationById(String occupationId) throws Exception;
	
	public List<Occupation> getAllOccupations() throws Exception;
	
}