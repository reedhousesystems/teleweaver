package com.reedhousesystems.services.core.health.dashboard.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.dashboard.api.LocationDao;
import com.reedhousesystems.services.core.health.dashboard.api.LocationService;
import com.reedhousesystems.services.core.health.dashboard.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.dashboard.model.Location;
import com.reedhousesystems.services.core.health.dashboard.requests.LocationRequest;

@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {
	
	@Resource
	private LocationDao locationDao;
	
	public void saveLocation(LocationRequest request) throws Exception {
		try {
			Location newLocation = new Location(request);
			locationDao.saveLocation(newLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateLocation(String locationId, LocationRequest request) throws Exception {
		try {
			Location locationToUpdate = locationDao.getLocationById(locationId);
			if (request.getProvince() != null) locationToUpdate.setProvince(request.getProvince());
			if (request.getDistrict() != null) locationToUpdate.setDistrict(request.getDistrict());
			if (request.getMunicipality() != null) locationToUpdate.setMunicipality(request.getMunicipality());
			if (request.getVillage() != null) locationToUpdate.setVillage(request.getVillage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void deleteLocation(String locationId) throws Exception {
		try {
			locationDao.deleteLocation(locationId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public LocationRequest getLocationById(String locationId) throws Exception {
		Location location = new Location();
		try {
			location = locationDao.getLocationById(locationId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (location == null)
			throw new ItemNotFoundException("Location Not Found", "Location requested cannot be found");
		return new LocationRequest(location);
	}

	public List<LocationRequest> getAllLocations() throws Exception {
		List<Location> locationList = new ArrayList<Location>();
		List<LocationRequest> locations = new ArrayList<LocationRequest>();
		try {
			locationList = locationDao.getAllLocations();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (Location location : locationList) {
			locations.add(new LocationRequest(location));
		}
		return locations;
	}

	public List<Location> getDistrictsByProvince(String province) throws Exception {
		List<Location> locationList = new ArrayList<Location>();
		try {
			locationList = locationDao.getDistrictsByProvince(province);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return locationList;
	}

	public List<Location> getMunicipalitiesByDistrict(String district) throws Exception {
		List<Location> locationList = new ArrayList<Location>();
		try {
			locationList = locationDao.getMunicipalitiesByDistrict(district);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return locationList;
	}

	public List<Location> getVillagesByMunicipality(String municipality) throws Exception {
		List<Location> locationList = new ArrayList<Location>();
		try {
			locationList = locationDao.getVillagesByMunicipality(municipality);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return locationList;
	}
	
}