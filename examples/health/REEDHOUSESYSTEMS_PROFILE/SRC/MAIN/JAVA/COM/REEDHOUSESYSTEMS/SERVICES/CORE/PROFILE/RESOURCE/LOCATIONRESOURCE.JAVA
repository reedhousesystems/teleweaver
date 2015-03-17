package com.reedhousesystems.services.core.profile.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reedhousesystems.services.core.profile.api.LocationService;
import com.reedhousesystems.services.core.profile.dto.DistrictDTO;
import com.reedhousesystems.services.core.profile.dto.MunicipalityDTO;
import com.reedhousesystems.services.core.profile.dto.VillageDTO;

@Path("/location")
@Component
@Produces({MediaType.APPLICATION_JSON})
public class LocationResource {
	
	@Autowired
	private LocationService locationService;

	@GET
	@Path("province/{province}")
	public Response getDistrictsByProvince(@PathParam("province") String province) throws Exception {
		List<DistrictDTO> locationList = new ArrayList<DistrictDTO>();
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
		List<MunicipalityDTO> locationList = new ArrayList<MunicipalityDTO>();
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
		List<VillageDTO> locationList = new ArrayList<VillageDTO>();
		try {
			locationList = locationService.getVillagesByMunicipality(municipality);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok().entity(locationList).build();
	}
	
}