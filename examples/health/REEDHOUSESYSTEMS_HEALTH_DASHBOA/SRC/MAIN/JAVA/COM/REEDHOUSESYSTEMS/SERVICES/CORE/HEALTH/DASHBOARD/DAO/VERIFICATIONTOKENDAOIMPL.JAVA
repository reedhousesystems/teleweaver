package com.reedhousesystems.services.core.health.dashboard.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.dashboard.api.VerificationTokenDao;
import com.reedhousesystems.services.core.health.dashboard.model.VerificationToken;

@Repository("verificationTokenDao")
public class VerificationTokenDaoImpl extends BaseDao implements VerificationTokenDao {

	public VerificationToken getVerificationTokenByUUID(String uuid) {
		VerificationToken verificationToken = null;
		Criteria criteria = getSession().createCriteria(VerificationToken.class)
			.add(Restrictions.eq("uuid", uuid));
		verificationToken = (VerificationToken) criteria.uniqueResult();
		return verificationToken;
	}

	public VerificationToken getVerificationTokenByToken(String token) {
		VerificationToken verificationToken = null;
		Criteria criteria = getSession().createCriteria(VerificationToken.class)
			.add(Restrictions.eq("token", token));
		verificationToken = (VerificationToken) criteria.uniqueResult();
		return verificationToken;
	}

	public <T> VerificationToken getVerificationToken(T field, T value) {
		VerificationToken verificationToken = null;
		Criteria criteria = getSession().createCriteria(VerificationToken.class)
			.add(Restrictions.eq(""+field+"", field));
		verificationToken = (VerificationToken) criteria.uniqueResult();
		return verificationToken;
	}
	
}