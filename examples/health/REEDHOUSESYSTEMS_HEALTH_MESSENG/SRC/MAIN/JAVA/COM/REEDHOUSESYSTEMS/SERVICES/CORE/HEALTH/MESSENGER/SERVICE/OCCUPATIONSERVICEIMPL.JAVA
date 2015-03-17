package com.reedhousesystems.services.core.health.messenger.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.messenger.api.OccupationDao;
import com.reedhousesystems.services.core.health.messenger.api.OccupationService;
import com.reedhousesystems.services.core.health.messenger.dto.OccupationDTO;
import com.reedhousesystems.services.core.health.messenger.model.Occupation;
import com.reedhousesystems.services.core.health.messenger.request.OccupationRequest;

@Service("occupationService")
@Transactional
public class OccupationServiceImpl implements OccupationService {
	
	@Resource
	private OccupationDao occupationDao;
	
	public void saveOccupation(OccupationRequest request) throws Exception {
		try {
			Occupation occupation = occupationDao.getOccupationById(request.getId());
			if (occupation != null) {
				occupation.setName(request.getName());
				occupation.setDescription(request.getDescription());
			} else 
				occupationDao.saveOccupation(new Occupation(request));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<OccupationDTO> getAllOccupations() throws Exception {
		List<Occupation> occupationList = new ArrayList<Occupation>();
		List<OccupationDTO> occupations = new ArrayList<OccupationDTO>();
		try {
			occupationList = occupationDao.getAllOccupations();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (Occupation occupation : occupationList) {
			occupations.add(new OccupationDTO(occupation));
		}
		return occupations;
	}	
}