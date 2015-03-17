package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.dto.OrganisationDTO;

public interface OrganisationService {
	
	public void saveOrganisation(OrganisationDTO organisationDTO) throws Exception;
	
	public void updateOrganisation(String organisationId, OrganisationDTO organisationDTO) throws Exception;
	
	public void deleteOrganisation(String organisationId) throws Exception;
	
	public OrganisationDTO getOrganisationById(String organisationId) throws Exception;
	
	public List<OrganisationDTO> getAllOrganisations() throws Exception;
	
}