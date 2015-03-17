package com.reedhousesystems.services.core.health.messenger.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.messenger.api.OrganisationDao;
import com.reedhousesystems.services.core.health.messenger.model.Organisation;

@Repository("OrganisationDao")
public class OrganisationDaoImpl extends BaseDao implements OrganisationDao {
	
	public void saveOrganisation(Organisation organisation) {
		getSession().save(organisation);
	}

	public Organisation getOrganisation(String organisationId) {
		Criteria criteria = getSession().createCriteria(Organisation.class)
			.add(Restrictions.eq("uuid", organisationId));
		Organisation organisation = (Organisation) criteria.uniqueResult();
		return organisation;
	}	
}