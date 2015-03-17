package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.model.Occupation;

public interface OccupationDao {
	
	public void saveOccupation(Occupation occupation) throws Exception;
	
	public void deleteOccupation(Occupation occupation) throws Exception;
	
	public Occupation getOccupationById(String occupationId) throws Exception;
	
	public List<Occupation> getAllOccupations() throws Exception;
	
}