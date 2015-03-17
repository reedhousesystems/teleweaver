package com.reedhousesystems.services.core.health.messenger.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.messenger.api.OrganisationDao;
import com.reedhousesystems.services.core.health.messenger.api.OrganisationService;
import com.reedhousesystems.services.core.health.messenger.dto.OrganisationDTO;
import com.reedhousesystems.services.core.health.messenger.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.messenger.model.Organisation;

@Service("organisationService")
@Transactional
public class OrganisationServiceImpl implements OrganisationService {
	
	@Resource
	private OrganisationDao organisationDao;

	public void saveOrUpdateOrganisation(OrganisationDTO organisation) {
		Organisation organisationToUpdate = organisationDao.getOrganisation(organisation.getId());
		if (organisationToUpdate != null) {
			organisationToUpdate.setName(organisation.getName());
			organisationToUpdate.setDescription(organisation.getDescription());
			organisationToUpdate.setAddressLine1(organisation.getAddressLine1());
			organisationToUpdate.setAddressLine2(organisation.getAddressLine2());
			organisationToUpdate.setAddressLine3(organisation.getAddressLine3());
			organisationToUpdate.setEmailAddress(organisation.getEmailAddress());
			organisationToUpdate.setPhoneNumber(organisation.getPhoneNumber());
		} else {
			organisationDao.saveOrganisation(new Organisation(organisation));
		}
	}

	public OrganisationDTO getOrganisation(String organisationId) {
		Organisation organisation = organisationDao.getOrganisation(organisationId);
		if (organisation == null)
			throw new ItemNotFoundException("Organisation not found", "Organisation requested cannot be found.");
		return new OrganisationDTO(organisation);
	}
	
}