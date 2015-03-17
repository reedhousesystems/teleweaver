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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.health.dashboard.model.Facility;
import com.reedhousesystems.services.core.health.dashboard.requests.FacilityRequest;

@Path("/facility")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class FacilityResource extends BaseServiceResource {
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void saveFacility(FacilityRequest request) throws Exception {
		try {
			serviceService.saveFacility(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateFacility(@PathParam("id") String facilityId, FacilityRequest request) throws Exception {
		try {
			serviceService.updateFacility(facilityId, request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}

	@DELETE
	@Path("{facilityid}")
	public Response deleteFacility(@PathParam("facilityid") String facilityId) throws Exception {
		try {
			serviceService.deleteFacility(facilityId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}

	@GET
	@Path("{id}")
	public Response getFacilityById(@PathParam("id") String facilityId) throws Exception {
		Facility facility = null;
		try {
			facility = serviceService.getFacilityById(facilityId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(facility).build();
	}

	@GET
	public Response getAllFacilities() throws Exception {
		List<Facility> facilityList = new ArrayList<Facility>();
		try {
			facilityList = serviceService.getAllFacilities();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(facilityList).build();
	}
	
}