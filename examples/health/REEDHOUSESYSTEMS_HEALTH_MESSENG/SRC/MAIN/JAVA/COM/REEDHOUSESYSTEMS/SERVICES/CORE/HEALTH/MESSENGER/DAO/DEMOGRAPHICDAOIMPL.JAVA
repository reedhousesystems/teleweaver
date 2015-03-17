package com.reedhousesystems.services.core.health.messenger.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.messenger.api.DemographicDao;
import com.reedhousesystems.services.core.health.messenger.model.Demographic;
import com.reedhousesystems.services.core.health.messenger.model.Target;

@Repository("demographicDao")
public class DemographicDaoImpl extends BaseDao implements DemographicDao {

	public Long getNumberOfMatchingUsers(Target target) {
		Criteria criteria = getSession().createCriteria(Demographic.class)
			.add(Restrictions.between("dateOfBirth", target.getDateOfBirthFrom(), target.getDateOfBirthTo()))
			.add(Restrictions.eq("gender", target.getGender()))
			.add(Restrictions.eq("language", target.getLanguage()))
			.add(Restrictions.eq("maritalStatus", target.getMaritalStatus()))
			.add(Restrictions.eq("occupation", target.getOccupation()))
			.add(Restrictions.eq("sexOrientation", target.getSexOrientation()))
			.add(Restrictions.eq("location", target.getLocation()));
		Long numberOfMatchingUsers = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return numberOfMatchingUsers;
	}
}