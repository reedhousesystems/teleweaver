package com.reedhousesystems.services.core.health.messenger.api;

import com.reedhousesystems.services.core.health.messenger.model.Organisation;


public interface OrganisationDao {
	
	public void saveOrganisation(Organisation organisation);
	
	public Organisation getOrganisation(String organisationId);
	
}