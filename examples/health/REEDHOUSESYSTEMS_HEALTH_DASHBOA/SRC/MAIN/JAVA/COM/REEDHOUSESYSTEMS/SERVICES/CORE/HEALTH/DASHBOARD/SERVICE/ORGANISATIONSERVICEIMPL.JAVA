package com.reedhousesystems.services.core.health.dashboard.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.dashboard.api.OrganisationDao;
import com.reedhousesystems.services.core.health.dashboard.api.OrganisationService;
import com.reedhousesystems.services.core.health.dashboard.dto.OrganisationDTO;
import com.reedhousesystems.services.core.health.dashboard.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.dashboard.model.Organisation;

@Service("organisationService")
@Transactional
public class OrganisationServiceImpl implements OrganisationService {
	
	@Resource
	private OrganisationDao organisationDao;

	public void saveOrganisation(OrganisationDTO organisationDTO)
			throws Exception {
		try {
			organisationDao.saveOrganisation(new Organisation(organisationDTO)); 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateOrganisation(String organisationId, OrganisationDTO organisationDTO)
			throws Exception {
		try {
			Organisation organisationToUpdate = organisationDao.getOrganisationById(organisationId);
			if (organisationDTO.getAddressLine1() != null) organisationToUpdate.setAddressLine1(organisationDTO.getAddressLine1());
			if (organisationDTO.getAddressLine2() != null) organisationToUpdate.setAddressLine2(organisationDTO.getAddressLine2());
			if (organisationDTO.getAddressLine3() != null) organisationToUpdate.setAddressLine3(organisationDTO.getAddressLine3());
			if (organisationDTO.getDescription() != null) organisationToUpdate.setDescription(organisationDTO.getDescription());
			if (organisationDTO.getName() != null) organisationToUpdate.setName(organisationDTO.getName());
			if (organisationDTO.getPhoneNumber() != null) organisationToUpdate.setPhoneNumber(organisationDTO.getPhoneNumber());
			if (organisationDTO.getEmailAddress() != null) organisationToUpdate.setEmailAddress(organisationDTO.getEmailAddress());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void deleteOrganisation(String organisationId) throws Exception {
		try {
			Organisation organisationToDelete = organisationDao.getOrganisationById(organisationId);
			if (organisationToDelete == null)
				throw new ItemNotFoundException("Organisation Not Found", "Organisation cannot be deleted because it does not exist");
			organisationDao.deleteOrganisation(organisationToDelete);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public OrganisationDTO getOrganisationById(String organisationId) throws Exception {
		Organisation organisation = null;
		try {
			organisation = organisationDao.getOrganisationById(organisationId);
			if (organisation == null)
				throw new ItemNotFoundException("Organisation Not Found", "Organisation cannot be deleted because it does not exist");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new OrganisationDTO(organisation);
	}

	public List<OrganisationDTO> getAllOrganisations() throws Exception {
		List<Organisation> organisations = new ArrayList<Organisation>();
		List<OrganisationDTO> organisationDTOs = new ArrayList<OrganisationDTO>();
		try {
			organisations = organisationDao.getAllOrganisations();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (organisations != null) {
			for (Organisation organisation : organisations) {
				organisationDTOs.add(new OrganisationDTO(organisation));
			}
		}
		return organisationDTOs;
	}
	
}