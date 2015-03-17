package com.reedhousesystems.services.core.profile.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.profile.api.OccupationDao;
import com.reedhousesystems.services.core.profile.api.OccupationService;
import com.reedhousesystems.services.core.profile.dto.OccupationDTO;
import com.reedhousesystems.services.core.profile.model.Occupation;

@Service("occupationService")
@Transactional
public class OccupationServiceImpl implements OccupationService {
	
	@Resource
	private OccupationDao occupationDao;

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