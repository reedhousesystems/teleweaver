package com.reedhousesystems.services.core.health.dashboard.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.health.dashboard.api.OrganisationService;
import com.reedhousesystems.services.core.health.dashboard.dto.OrganisationDTO;

@Path("/organisation")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class OrganisationResource {
	
	@Autowired
	OrganisationService organisationService;
		
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveOrganisation(OrganisationDTO organisationDTO) throws Exception {
		try {
			organisationService.saveOrganisation(organisationDTO);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().build();
	}
	
	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateOrganisation(@PathParam("id") String organisationId, OrganisationDTO organisationDTO) throws Exception {
		try {
			organisationService.updateOrganisation(organisationId, organisationDTO);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteOrganisation(@PathParam("id") String organisationId) throws Exception {
		try {
			organisationService.deleteOrganisation(organisationId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().build();
	}
	
	@GET
	@Path("{id}")
	public Response getOrganisationById(@PathParam("id") String organisationId) throws Exception {
		OrganisationDTO organisationDTO = null;
		try {
			organisationDTO = organisationService.getOrganisationById(organisationId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(organisationDTO).build();
	}
	
	@GET
	public Response getAllOrganisations() throws Exception {
		List<OrganisationDTO> organisations;
		try {
			organisations = organisationService.getAllOrganisations();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(organisations).build();
	}
	
}