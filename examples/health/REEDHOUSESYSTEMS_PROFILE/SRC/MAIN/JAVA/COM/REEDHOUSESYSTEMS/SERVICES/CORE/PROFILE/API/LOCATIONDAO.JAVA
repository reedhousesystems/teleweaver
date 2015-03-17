package com.reedhousesystems.services.core.profile.api;

import java.util.List;

import com.reedhousesystems.services.core.profile.dto.LocationDTO;
import com.reedhousesystems.services.core.profile.model.Location;

public interface LocationDao {
	
	public Location getLocation(LocationDTO locationDTO);
	
	public List<Location> getDistrictsByProvince(String province) throws Exception;
	
	public List<Location> getMunicipalitiesByDistrict(String district) throws Exception;
	
	public List<Location> getVillagesByMunicipality(String municipality) throws Exception;
	
}