package com.reedhousesystems.services.core.profile.api;

import java.util.List;

import com.reedhousesystems.services.core.profile.model.Occupation;

public interface OccupationDao {
	
	public List<Occupation> getAllOccupations() throws Exception;
	
}