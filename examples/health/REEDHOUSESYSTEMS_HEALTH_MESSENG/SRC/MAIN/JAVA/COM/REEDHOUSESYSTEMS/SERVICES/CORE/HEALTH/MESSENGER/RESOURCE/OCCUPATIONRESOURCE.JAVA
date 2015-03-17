package com.reedhousesystems.services.core.health.messenger.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.health.messenger.api.OccupationService;
import com.reedhousesystems.services.core.health.messenger.dto.OccupationDTO;
import com.reedhousesystems.services.core.health.messenger.filter.CorsPreflight;
import com.reedhousesystems.services.core.health.messenger.request.OccupationRequest;

@Path("/occupation")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class OccupationResource {
	
	@Autowired
	private OccupationService occupationService;
	
	@OPTIONS
	@CorsPreflight
	public Response optionRequest() {
		return Response.ok().build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveOccupation(OccupationRequest request) throws Exception {
		try {
			occupationService.saveOccupation(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
        return Response.noContent().build();
	}
	
	@GET
	public Response getAllOccupations() throws Exception {
		List<OccupationDTO> occupations = new ArrayList<OccupationDTO>();
		try {
			occupations = occupationService.getAllOccupations();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(occupations).build();
	}
	
}