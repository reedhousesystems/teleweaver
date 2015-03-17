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

import com.reedhousesystems.services.core.health.dashboard.api.ServiceService;
import com.reedhousesystems.services.core.health.dashboard.dto.ServiceDTO;
import com.reedhousesystems.services.core.health.dashboard.model.Services;
import com.reedhousesystems.services.core.health.dashboard.requests.ServiceRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.ServiceTypeRequest;

@Path("/service")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class ServiceResource {
	
	@Autowired
	private ServiceService serviceService;

    @Context
    protected UriInfo uriInfo;

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveService(ServiceRequest request) throws Exception {
		try {
			serviceService.saveService(request);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}

	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response updateService(@PathParam("id") String serviceId, ServiceRequest request) throws Exception {
		try {
			serviceService.updateService(serviceId, request);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteService(@PathParam("id") String serviceId) throws Exception {
		try {
			serviceService.deleteService(serviceId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}

	@GET
	@Path("{id}")
	public Response getServiceById(@PathParam("id") String serviceId) throws Exception {
		ServiceRequest services = null;
		try {
			services = serviceService.getServiceById(serviceId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(services).build();
	}

	@GET
	public Response getAllServices() throws Exception {
		List<ServiceRequest> serviceList = new ArrayList<ServiceRequest>();
		try {
			serviceList = serviceService.getAllServices();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(serviceList).build();
	}
	
	@PUT
	@Path("facility/{facilityId}/add")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response addServicesToAFacility(@PathParam("facilityId") String facilityId, ServiceDTO serviceDTO) throws Exception {
		try {
			serviceService.addServicesToAFacility(serviceDTO.getId(), facilityId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}
	
	@PUT
	@Path("facility/{facilityId}/remove")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response removeServicesFromAFacility(@PathParam("facilityId") String facilityId, ServiceDTO serviceDTO) throws Exception {
		try {
			serviceService.removeServicesFromAFacility(serviceDTO.getId(), facilityId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}
	
	@GET
	@Path("facility/{id}")
	@Produces("application/json")
	public Response getServicesByFacilityId(@PathParam("id") String facilityId) throws Exception {
		List<Services> serviceList = new ArrayList<Services>();
		try {
			serviceList = serviceService.getServicesByFacilityId(facilityId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}
	
	@GET
	@Path("facility/{id}/notoffered")
	public Response getServicesNotOfferedByAFacility(@PathParam("id") String facilityId) throws Exception {
		List<Services> serviceList = new ArrayList<Services>();
		try {
			serviceList = serviceService.getServicesNotOfferedByAFacility(facilityId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(serviceList).build();
	}

	@POST
	@Path("type")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveServiceType(ServiceTypeRequest request) throws Exception {
		try {
			serviceService.saveServiceType(request);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}

	@PUT
	@Path("type/{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces("application/json")
	public Response updateServiceType(@PathParam("id") String serviceTypeId, ServiceTypeRequest request) throws Exception {
		try {
			serviceService.updateServiceType(serviceTypeId, request);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}

	@DELETE
	@Path("type/{id}")
	public Response deleteServiceType(@PathParam("id") String serviceTypeId) throws Exception {
		try {
			serviceService.deleteServiceType(serviceTypeId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.noContent().build();
	}

	@GET
	@Path("type/{id}")
	@Produces("application/json")
	public Response getServiceTypeById(@PathParam("id") String serviceTypeId) throws Exception {
		ServiceTypeRequest serviceType = null;
		try {
			serviceType = serviceService.getServiceTypeById(serviceTypeId);
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(serviceType).build();
	}

	@GET
	@Path("type")
	public Response getAllServiceTypes() throws Exception {
		List<ServiceTypeRequest> serviceTypeList = new ArrayList<ServiceTypeRequest>();
		try {
			serviceTypeList = serviceService.getAllServiceTypes();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
		return Response.ok().entity(serviceTypeList).build();
	}
	
}