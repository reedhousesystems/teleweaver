package com.reedhousesystems.services.core.profile.api;

import java.util.List;

import com.reedhousesystems.services.core.profile.dto.DistrictDTO;
import com.reedhousesystems.services.core.profile.dto.MunicipalityDTO;
import com.reedhousesystems.services.core.profile.dto.VillageDTO;

public interface LocationService {
	
	public List<DistrictDTO> getDistrictsByProvince(String province) throws Exception;
	
	public List<MunicipalityDTO> getMunicipalitiesByDistrict(String district) throws Exception;
	
	public List<VillageDTO> getVillagesByMunicipality(String municipality) throws Exception;
	
}