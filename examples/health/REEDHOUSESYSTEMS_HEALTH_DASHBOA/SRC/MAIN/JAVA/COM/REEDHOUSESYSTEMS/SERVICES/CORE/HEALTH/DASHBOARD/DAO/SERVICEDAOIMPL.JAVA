package com.reedhousesystems.services.core.health.dashboard.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.reedhousesystems.services.core.health.dashboard.api.ServiceDao;
import com.reedhousesystems.services.core.health.dashboard.model.Facility;
import com.reedhousesystems.services.core.health.dashboard.model.ServiceType;
import com.reedhousesystems.services.core.health.dashboard.model.Services;

@Repository("serviceDao")
public class ServiceDaoImpl extends BaseDao implements ServiceDao {
	
	public void saveFacility(Facility facility) throws Exception {
		try {
			getSession().save(facility);
		} catch (HibernateException e) {	
			throw new Exception("An error occured while adding a facility", e);
		}
	}
	
	public void deleteFacility(String facilityId) throws Exception {
		Facility facilityToDelete = getFacilityById(facilityId);
		facilityToDelete.getServices().clear();	
		getSession().delete(facilityToDelete);
	}
	
	public Facility getFacilityById(String facilityId) throws Exception {
		Criteria criteria = getSession().createCriteria(Facility.class)
			.add(Restrictions.eq("uuid", facilityId));
		Facility facility = (Facility) criteria.uniqueResult();
		return facility;
	}
	
	public List<Facility> getAllFacilities() throws Exception {
		List<Facility> facilityList = new ArrayList<Facility>();
		try {
			Criteria criteria = getSession().createCriteria(Facility.class)
				.setProjection(Projections.distinct(Projections.projectionList()
					.add(Projections.property("name"))
					.add(Projections.property("type"))
					.add(Projections.property("address"))
					.add(Projections.property("uuid"))));
			facilityList = criteria.list();
		} catch (HibernateException e) {	
			throw new Exception("An error occurred while getting all the facilities", e);
		}
		return facilityList;
	}
	
	public void saveService(Services service) throws Exception {
		try {
			getSession().save(service);
		} catch (HibernateException e) {	
			throw new Exception("An error occurred while adding a service", e);
		}
	}
	
	public void deleteService(String serviceId) throws Exception {
		Services serviceToDelete = getServiceById(serviceId);
		getSession().delete(serviceToDelete);
	}
	
	public Services getServiceById(String serviceId) throws Exception {
		Criteria criteria = getSession().createCriteria(Services.class)
			.add(Restrictions.eq("uuid", serviceId));
		Services service = (Services) criteria.uniqueResult();
		return service;
	}
	
	public List<Services> getAllServices() throws Exception {
		List<Services> serviceList = new ArrayList<Services>();
		try {
			Criteria criteria = getSession().createCriteria(Services.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			serviceList = criteria.list();
		} catch (HibernateException e) {	
			throw new Exception("An error occurred hile getting all the services", e);
		}
		return serviceList;
	}
	
	public List<Services> getServicesNotOfferedByAFacility(String facilityId) throws Exception {
		List<Services> servicesList = new ArrayList<Services>();
		try {
			Facility facility = getFacilityById(facilityId);
			List<Services> servicesInAFacility = facility.getServices();
			Criteria criteria = getSession().createCriteria(Services.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);	
			servicesList = criteria.list();
			for (Services service : servicesInAFacility) {
				servicesList.remove(service);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return servicesList;
	}
	
	public void saveServiceType(ServiceType serviceType) throws Exception {
		try {
			getSession().save(serviceType);
		} catch (HibernateException e) {
			throw new Exception("An error occurred while adding a service", e);
		}
	}
	
	public void deleteServiceType(String serviceTypeId) throws Exception {
		ServiceType serviceTypeToDelete = getServiceTypeById(serviceTypeId);
		getSession().delete(serviceTypeToDelete);
	}
	
	public ServiceType getServiceTypeById(String serviceTypeId) throws Exception {
		Criteria criteria = getSession().createCriteria(ServiceType.class)
			.add(Restrictions.eq("uuid", serviceTypeId));
		ServiceType serviceType = (ServiceType) criteria.uniqueResult();
		return serviceType;
	}
	
	public List<ServiceType> getAllServiceTypes() throws Exception {
		List<ServiceType> serviceTypeList = new ArrayList<ServiceType>();
		try {
			Criteria criteria = getSession().createCriteria(ServiceType.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			serviceTypeList = criteria.list();
		} catch (HibernateException e) {
			throw new Exception("An error occurred while getting all the services", e);
		}
		return serviceTypeList;
	}
	
}