package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.dto.DistrictDTO;
import com.reedhousesystems.services.core.health.messenger.dto.MunicipalityDTO;
import com.reedhousesystems.services.core.health.messenger.dto.VillageDTO;
import com.reedhousesystems.services.core.health.messenger.request.LocationRequest;

public interface LocationService {
	
	public void saveLocation(LocationRequest request) throws Exception;
	
	public List<DistrictDTO> getDistrictsByProvince(String province) throws Exception;
	
	public List<MunicipalityDTO> getMunicipalitiesByDistrict(String district) throws Exception;
	
	public List<VillageDTO> getVillagesByMunicipality(String municipality) throws Exception;
	
}