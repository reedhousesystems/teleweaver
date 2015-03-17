package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.dto.LocationDTO;
import com.reedhousesystems.services.core.health.messenger.model.Location;

public interface LocationDao {
	
	public void saveLocation(Location location) throws Exception;
	
	public Location getLocationById(String locationId) throws Exception;
	
	public Location getLocation(LocationDTO locationDTO);
	
	public List<Location> getDistrictsByProvince(String province) throws Exception;
	
	public List<Location> getMunicipalitiesByDistrict(String district) throws Exception;
	
	public List<Location> getVillagesByMunicipality(String municipality) throws Exception;
	
}