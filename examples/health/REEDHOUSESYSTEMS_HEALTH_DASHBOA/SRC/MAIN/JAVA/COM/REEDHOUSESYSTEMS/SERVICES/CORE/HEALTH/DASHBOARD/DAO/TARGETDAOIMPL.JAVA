package com.reedhousesystems.services.core.health.dashboard.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.dashboard.api.TargetDao;
import com.reedhousesystems.services.core.health.dashboard.model.Target;

@Repository("targetDao")
public class TargetDaoImpl extends BaseDao implements TargetDao {

	public Target saveTarget(Target target) {
		Long id = (Long) getSession().save(target);
		target.setId(id);
		return target;
	}

	public Target getTarget(String targetId) {
		Criteria criteria = getSession().createCriteria(Target.class)
			.add(Restrictions.eq("uuid", targetId));
		Target target = (Target) criteria.uniqueResult();
		return target;
	}
	
	public Target getTarget(Target target) {
		Criteria criteria = getSession().createCriteria(Target.class)
			.add(Restrictions.eq("language", target.getLanguage()))
			.add(Restrictions.eq("dateOfBirthFrom", target.getDateOfBirthFrom()))
			.add(Restrictions.eq("dateOfBirthTo", target.getDateOfBirthTo()))
			.add(Restrictions.eq("sex", target.getSex()))
			.add(Restrictions.eq("occupation", target.getOccupation()))
			.add(Restrictions.eq("maritalStatus", target.getMaritalStatus()))
			.add(Restrictions.eq("sexOrientation", target.getSexOrientation()))
			.add(Restrictions.eq("location", target.getLocation()));
		Target targetRetrieved = (Target) criteria.uniqueResult();
		return targetRetrieved;
	}
	
}