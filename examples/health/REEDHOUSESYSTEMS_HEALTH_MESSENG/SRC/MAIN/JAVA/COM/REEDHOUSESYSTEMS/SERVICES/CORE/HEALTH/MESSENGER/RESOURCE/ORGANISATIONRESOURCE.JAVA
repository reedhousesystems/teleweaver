package com.reedhousesystems.services.core.health.messenger.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.health.messenger.api.OrganisationService;
import com.reedhousesystems.services.core.health.messenger.dto.OrganisationDTO;
import com.reedhousesystems.services.core.health.messenger.filter.CorsPreflight;

@Path("/organisation")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class OrganisationResource {
	
	@Autowired
	private OrganisationService organisationService;
	
	@OPTIONS
	@CorsPreflight
	public Response optionRequest() {
		return Response.ok().build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveOrUpdateOrganisation(OrganisationDTO organisation) {
		organisationService.saveOrUpdateOrganisation(organisation);
		return Response.noContent().build();
	}
}