package com.reedhousesystems.services.core.health.dashboard.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.dashboard.api.OrganisationDao;
import com.reedhousesystems.services.core.health.dashboard.model.Organisation;

@Repository("organisationDao")
public class OrganisationDaoImpl extends BaseDao implements OrganisationDao {

	public void saveOrganisation(Organisation organisation) throws Exception {
		try {
			getSession().save(organisation);
		} catch (HibernateException hex) {
			throw new HibernateException("Error saving an organisation", hex);
		}
	}

	public void deleteOrganisation(Organisation organisation) throws Exception {
		try {
			getSession().delete(organisation);
		} catch (HibernateException hex) {
			throw new HibernateException ("Error deleting an organisation", hex);
		}
	}

	public Organisation getOrganisationById(String organisationId)
			throws Exception {
		Organisation organisation = null;
		try {
			Criteria criteria = getSession().createCriteria(Organisation.class)
				.add(Restrictions.eq("uuid", organisationId));
			organisation = (Organisation) criteria.uniqueResult();
		} catch (HibernateException hex) {
			throw new HibernateException("Error getting organisation details", hex);
		}
		return organisation;
	}

	public List<Organisation> getAllOrganisations() throws Exception {
		List<Organisation> organisations = new ArrayList<Organisation>();
		try {
			Criteria criteria = getSession().createCriteria(Organisation.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			organisations = criteria.list();
		} catch (HibernateException hex) {
			throw new HibernateException ("Error getting all organisations", hex);
		}
		return organisations;
	}
		
}