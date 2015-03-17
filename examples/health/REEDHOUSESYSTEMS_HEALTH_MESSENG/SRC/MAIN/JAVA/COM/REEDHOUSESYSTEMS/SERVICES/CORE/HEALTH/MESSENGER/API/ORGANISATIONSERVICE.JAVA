package com.reedhousesystems.services.core.health.messenger.api;

import com.reedhousesystems.services.core.health.messenger.dto.OrganisationDTO;


public interface OrganisationService {
	
	public void saveOrUpdateOrganisation(OrganisationDTO organisation);
	
	public OrganisationDTO getOrganisation(String organisationId);
}