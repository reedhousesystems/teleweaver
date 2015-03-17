package com.reedhousesystems.services.core.health.messenger.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.messenger.api.ServiceDao;
import com.reedhousesystems.services.core.health.messenger.model.Facility;
import com.reedhousesystems.services.core.health.messenger.model.Service;

@Repository("ServiceDao")
public class ServiceDaoImpl extends BaseDao implements ServiceDao {

	public void saveFacility(Facility facility) {
		Facility facilityToDelete = getFacilityById(facility.getUuid().toString());
		if (facilityToDelete != null)
			getSession().delete(facilityToDelete);
		getSession().save(facility);
	}
	
	public Facility getFacilityById(String facilityId) {
		Criteria criteria = getSession().createCriteria(Facility.class)
			.add(Restrictions.eq("uuid", facilityId));
		Facility facility = (Facility) criteria.uniqueResult();
		return facility;
	}

	public List<Facility> getAllFacilities() {
		List<Facility> facilityList = new ArrayList<Facility>();
		Criteria criteria = getSession().createCriteria(Facility.class)
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		facilityList = criteria.list();
		return facilityList;
	}

	public void saveService(Service service) {
		Service serviceToDelete = getServiceById(service.getUuid().toString());
		if (serviceToDelete != null)
			getSession().delete(serviceToDelete);
		getSession().save(service);
	}

	public Service getServiceById(String serviceId) {
		Criteria criteria = getSession().createCriteria(Service.class)
			.add(Restrictions.eq("uuid", serviceId));
		Service service = (Service) criteria.uniqueResult();
		return service;
	}

	public Service searchService(String name) {
		Criteria criteria = getSession().createCriteria(Service.class)
			.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		Service service = (Service) criteria.uniqueResult();
		return service;
	}

	public List<Service> getServicesByFacilityId(String facilityId) {
		Facility facility = getFacilityById(facilityId);
		return facility.getServices();
	}
	
}