package com.reedhousesystems.services.core.health.dashboard.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.dashboard.api.OccupationDao;
import com.reedhousesystems.services.core.health.dashboard.api.OccupationService;
import com.reedhousesystems.services.core.health.dashboard.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.dashboard.model.Occupation;
import com.reedhousesystems.services.core.health.dashboard.requests.OccupationRequest;

@Service("occupationService")
@Transactional
public class OccupationServiceImpl implements OccupationService {
	
	@Resource
	private OccupationDao occupationDao;
	
	public void saveOccupation(OccupationRequest request) throws Exception {
		try {
			Occupation occupationToSave = new Occupation(request);
			occupationDao.saveOccupation(occupationToSave);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateOccupation(String occupationId, OccupationRequest request) throws Exception {
		try {
			Occupation occupationToUpdate = occupationDao.getOccupationById(occupationId);
			if (request.getName() != null) occupationToUpdate.setName(request.getName());
			if (request.getDescription() != null) occupationToUpdate.setDescription(request.getDescription());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void deleteOccupation(String occupationId) throws Exception {
		try {
			Occupation occupation = occupationDao.getOccupationById(occupationId);
			if (occupation == null)
				throw new ItemNotFoundException("Occupation Not Found", "Occcupation requested cannot be found");
			occupationDao.deleteOccupation(occupation);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public OccupationRequest getOccupationById(String occupationId) throws Exception {
		Occupation occupation = new Occupation();
		try {
			occupation = occupationDao.getOccupationById(occupationId);
			if (occupation == null)
				throw new ItemNotFoundException("Occupation Not Found", "Occcupation requested cannot be found");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new OccupationRequest(occupation);
	}

	public List<OccupationRequest> getAllOccupations() throws Exception {
		List<Occupation> occupationList = new ArrayList<Occupation>();
		List<OccupationRequest> occupations = new ArrayList<OccupationRequest>();
		try {
			occupationList = occupationDao.getAllOccupations();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (occupations != null) {
			for (Occupation occupation : occupationList) {
				occupations.add(new OccupationRequest(occupation));
			}
		}
		return occupations;
	}
	
}