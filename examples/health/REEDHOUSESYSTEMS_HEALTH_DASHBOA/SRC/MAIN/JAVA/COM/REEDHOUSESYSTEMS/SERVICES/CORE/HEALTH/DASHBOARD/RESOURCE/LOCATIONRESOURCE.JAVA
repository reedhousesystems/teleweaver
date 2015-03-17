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

import com.reedhousesystems.services.core.health.dashboard.api.LocationService;
import com.reedhousesystems.services.core.health.dashboard.model.Location;
import com.reedhousesystems.services.core.health.dashboard.requests.LocationRequest;

@Path("/location")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class LocationResource {
	
	@Autowired
	private LocationService locationService;

    @Context
    protected UriInfo uriInfo;

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response saveLocation(LocationRequest request) throws Exception {
		try {
			locationService.saveLocation(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}

	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces("application/json")
	public Response updateLocation(@PathParam("id") String locationId, LocationRequest request) throws Exception {
		try {
			locationService.updateLocation(locationId, request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteLocation(@PathParam("id") String locationId) throws Exception {
		try {
			locationService.deleteLocation(locationId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.noContent().build();
	}

	@GET
	@Path("{id}")
	@Produces("application/json")
	public Response getLocationById(@PathParam("id") String locationId) throws Exception {
		LocationRequest location = null;
		try {
			location = locationService.getLocationById(locationId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(location).build();
	}

	@GET
	public Response getAllLocations() throws Exception {
		List<LocationRequest> locationList = new ArrayList<LocationRequest>();
		try {
			locationList = locationService.getAllLocations();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(locationList).build();
	}

	@GET
	@Path("province/{province}")
	public Response getDistrictsByProvince(@PathParam("province") String province) throws Exception {
		List<Location> locationList = new ArrayList<Location>();
		try {
			locationList = locationService.getDistrictsByProvince(province);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(locationList).build();
	}

	@GET
	@Path("district/{district}")
	public Response getMunicipalitiesByDistrict(@PathParam("district") String district) throws Exception {
		List<Location> locationList = new ArrayList<Location>();
		try {
			locationList = locationService.getMunicipalitiesByDistrict(district);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(locationList).build();
	}

	@GET
	@Path("municipality/{municipality}")
	@Produces("application/json")
	public Response getVillagesByMunicipality(@PathParam("municipality") String municipality) throws Exception {
		List<Location> locationList = new ArrayList<Location>();
		try {
			locationList = locationService.getVillagesByMunicipality(municipality);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(locationList).build();
	}
	
}