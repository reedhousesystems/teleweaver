package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.dto.LocationDTO;
import com.reedhousesystems.services.core.health.dashboard.model.Location;

public interface LocationDao {
	
	public void saveLocation(Location location) throws Exception;
	
	public void deleteLocation(String locationId) throws Exception;
	
	public Location getLocationById(String locationId) throws Exception;
	
	public Location getLocation(LocationDTO locationDTO);
	
	public List<Location> getAllLocations() throws Exception;
	
	public List<Location> getDistrictsByProvince(String province) throws Exception;
	
	public List<Location> getMunicipalitiesByDistrict(String district) throws Exception;
	
	public List<Location> getVillagesByMunicipality(String municipality) throws Exception;
	
}