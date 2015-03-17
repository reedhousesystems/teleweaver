package com.reedhousesystems.services.core.profile.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.profile.api.OccupationDao;
import com.reedhousesystems.services.core.profile.model.Occupation;

@Repository("occupationDao")
public class OccupationDaoImpl extends BaseDao implements OccupationDao {
	
	public List<Occupation> getAllOccupations() throws Exception {
		List<Occupation> occupationList = new ArrayList<Occupation>();
		try {
			Criteria criteria = getSession().createCriteria(Occupation.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			occupationList = criteria.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return occupationList;
	}
	
}