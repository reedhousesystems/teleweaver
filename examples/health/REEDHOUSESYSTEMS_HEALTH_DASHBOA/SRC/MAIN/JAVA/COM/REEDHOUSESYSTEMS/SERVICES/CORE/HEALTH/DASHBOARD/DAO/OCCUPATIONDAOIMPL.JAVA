package com.reedhousesystems.services.core.health.dashboard.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.dashboard.api.OccupationDao;
import com.reedhousesystems.services.core.health.dashboard.model.Occupation;

@Repository("occupationDao")
public class OccupationDaoImpl extends BaseDao implements OccupationDao {
	
	public void saveOccupation(Occupation occupation) throws Exception {
		try {
			getSession().save(occupation);
		} catch (HibernateException ex) {
			throw new Exception("An error occurred while adding occupation");
		}
	}
	
	public void deleteOccupation(Occupation occupation) throws Exception {
		try {
			getSession().delete(occupation);
		} catch (HibernateException ex) {
			throw new Exception("An error occurred while deleting occupation");
		}
	}
	
	public Occupation getOccupationById(String occupationId) throws Exception {
		Occupation occupation = null;
		try {
			Criteria criteria = getSession().createCriteria(Occupation.class)
				.add(Restrictions.eq("uuid", occupationId));
			occupation = (Occupation) criteria.uniqueResult();
		} catch (HibernateException ex) {
			throw new Exception("An error occurred while getting occupation");
		}
		return occupation;
	}
	
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