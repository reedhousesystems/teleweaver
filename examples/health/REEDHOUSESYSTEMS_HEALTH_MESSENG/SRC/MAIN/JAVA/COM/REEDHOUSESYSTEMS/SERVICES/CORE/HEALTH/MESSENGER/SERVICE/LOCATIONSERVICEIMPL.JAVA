package com.reedhousesystems.services.core.health.messenger.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.messenger.api.LocationDao;
import com.reedhousesystems.services.core.health.messenger.api.LocationService;
import com.reedhousesystems.services.core.health.messenger.dto.DistrictDTO;
import com.reedhousesystems.services.core.health.messenger.dto.MunicipalityDTO;
import com.reedhousesystems.services.core.health.messenger.dto.VillageDTO;
import com.reedhousesystems.services.core.health.messenger.model.Location;
import com.reedhousesystems.services.core.health.messenger.request.LocationRequest;

@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {
	
	@Resource
	private LocationDao locationDao;
	
	public void saveLocation(LocationRequest request) throws Exception {
		try {
			Location location = locationDao.getLocationById(request.getId());
			if (location != null) {
				location.setProvince(request.getProvince());
				location.setDistrict(request.getDistrict());
				location.setMunicipality(request.getMunicipality());
				location.setVillage(request.getVillage());
			} else
				locationDao.saveLocation(new Location(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<DistrictDTO> getDistrictsByProvince(String province) throws Exception {
		List<Location> locationList = new ArrayList<Location>();
		List<DistrictDTO> districts = new ArrayList<DistrictDTO>();
		try {
			locationList = locationDao.getDistrictsByProvince(province);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (Location location : locationList) {
			districts.add(new DistrictDTO(location));
		}
		return districts;
	}

	public List<MunicipalityDTO> getMunicipalitiesByDistrict(String district) throws Exception {
		List<Location> locationList = new ArrayList<Location>();
		List<MunicipalityDTO> municipalities = new ArrayList<MunicipalityDTO>();
		try {
			locationList = locationDao.getMunicipalitiesByDistrict(district);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (Location location : locationList) {
			municipalities.add(new MunicipalityDTO(location));
		}
		return municipalities;
	}

	public List<VillageDTO> getVillagesByMunicipality(String municipality) throws Exception {
		List<Location> locationList = new ArrayList<Location>();
		List<VillageDTO> villages = new ArrayList<VillageDTO>();
		try {
			locationList = locationDao.getVillagesByMunicipality(municipality);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (Location location : locationList) {
			villages.add(new VillageDTO(location));
		}
		return villages;
	}
	
}