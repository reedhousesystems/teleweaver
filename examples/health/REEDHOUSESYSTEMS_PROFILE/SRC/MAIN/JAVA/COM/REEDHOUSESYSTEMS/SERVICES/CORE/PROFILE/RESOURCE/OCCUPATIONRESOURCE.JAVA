package com.reedhousesystems.services.core.profile.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.profile.api.OccupationService;
import com.reedhousesystems.services.core.profile.dto.OccupationDTO;

@Path("/occupation")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class OccupationResource {
	
	@Autowired
	private OccupationService occupationService;
	
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