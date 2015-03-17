package com.reedhousesystems.services.core.health.dashboard.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.health.dashboard.api.OccupationService;
import com.reedhousesystems.services.core.health.dashboard.requests.OccupationRequest;

@Path("/occupation")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class OccupationResource {
	
	@Autowired
	private OccupationService occupationService;

    @Context
    protected UriInfo uriInfo;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveOccupation(OccupationRequest request) throws Exception {
		try {
			occupationService.saveOccupation(request);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}
	
	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateOccupation(@PathParam("id") String occupationId, OccupationRequest request) throws Exception {
		try {
			occupationService.updateOccupation(occupationId, request);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteOccupation(@PathParam("id") String occupationId) throws Exception {
		try {
			occupationService.deleteOccupation(occupationId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}
	
	@GET
	@Path("{id}")
	public Response getOccupationById(@PathParam("id") String occupationId) throws Exception {
		OccupationRequest occupation = null;
		try {
			occupation = occupationService.getOccupationById(occupationId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(occupation).build();
	}
	
	@GET
	public Response getAllOccupations() throws Exception {
		List<OccupationRequest> occupationList = new ArrayList<OccupationRequest>();
		try {
			occupationList = occupationService.getAllOccupations();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(occupationList).build();
	}
	
}