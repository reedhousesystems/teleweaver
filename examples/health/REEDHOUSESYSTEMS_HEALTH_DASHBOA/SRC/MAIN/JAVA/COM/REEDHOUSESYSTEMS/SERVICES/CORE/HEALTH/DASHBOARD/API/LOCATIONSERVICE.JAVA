package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.model.Location;
import com.reedhousesystems.services.core.health.dashboard.requests.LocationRequest;

public interface LocationService {
	
	public void saveLocation(LocationRequest request) throws Exception;
	
	public void updateLocation(String locationId, LocationRequest request) throws Exception;
	
	public void deleteLocation(String locationId) throws Exception;
	
	public LocationRequest getLocationById(String locationId) throws Exception;
	
	public List<LocationRequest> getAllLocations() throws Exception;
	
	public List<Location> getDistrictsByProvince(String province) throws Exception;
	
	public List<Location> getMunicipalitiesByDistrict(String district) throws Exception;
	
	public List<Location> getVillagesByMunicipality(String municipality) throws Exception;
	
}