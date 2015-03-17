package com.reedhousesystems.services.core.health.messenger.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.health.messenger.api.ServiceService;
import com.reedhousesystems.services.core.health.messenger.model.Facility;
import com.reedhousesystems.services.core.health.messenger.model.Service;
import com.reedhousesystems.services.core.health.messenger.request.FacilityRequest;
import com.reedhousesystems.services.core.health.messenger.request.ServiceRequest;

@Path("/service")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class ServiceResource {
	
	@Autowired
	private ServiceService serviceService;
	
	@POST
	@Path("facility")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveFacility(Facility facility) {
		serviceService.saveFacility(facility);
		return Response.noContent().build();
	}

	@GET
	@Path("facility/{id}")
	public Response getFacilityById(@PathParam("id") String facilityId) {
		FacilityRequest facility = serviceService.getFacilityById(facilityId);
		return Response.ok().entity(facility).build();
	}

	@GET
	@Path("facility")
	public Response getAllFacilities() {
		List<FacilityRequest> facilityList = new ArrayList<FacilityRequest>();
		facilityList = serviceService.getAllFacilities();
		return Response.ok().entity(facilityList).build();
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveService(Service service) {
		serviceService.saveService(service);
		return Response.noContent().build();
	}

	@GET
	@Path("{id}")
	public Response getServiceById(@PathParam("id") String serviceId) {
		ServiceRequest service = serviceService.getServiceById(serviceId);
		return Response.ok().entity(service).build();
	}

	@GET
	@Path("{name}/search")
	public Response searchService(@PathParam("name") String name) {
		ServiceRequest service = serviceService.searchService(name);
		return Response.ok().entity(service).build();
	}
	
	@GET
	@Path("facility/{id}/services")
	public Response getServicesByFacilityId(@PathParam("id") String facilityId) {
		List<ServiceRequest> serviceList = serviceService.getServicesByFacilityId(facilityId);
		return Response.ok().entity(serviceList).build();
	}
	
}