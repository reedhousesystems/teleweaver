package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.model.Organisation;

public interface OrganisationDao {
	
	public void saveOrganisation(Organisation organisation) throws Exception;
	
	public void deleteOrganisation(Organisation organisationId) throws Exception;
	
	public Organisation getOrganisationById(String organisationId) throws Exception;
	
	public List<Organisation> getAllOrganisations() throws Exception;
	
}