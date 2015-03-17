package com.reedhousesystems.services.core.health.messenger.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.messenger.api.ServiceDao;
import com.reedhousesystems.services.core.health.messenger.api.ServiceService;
import com.reedhousesystems.services.core.health.messenger.exception.ItemNotFoundException;
import com.reedhousesystems.services.core.health.messenger.model.Facility;
import com.reedhousesystems.services.core.health.messenger.request.FacilityRequest;
import com.reedhousesystems.services.core.health.messenger.request.ServiceRequest;

@Service("ServiceService")
@Transactional
public class ServiceServiceImpl implements ServiceService {

	@Autowired
	private ServiceDao serviceDao;

	public void saveFacility(Facility facility) {
		serviceDao.saveFacility(facility);
	}
	
	public FacilityRequest getFacilityById(String facilityId) {
		Facility facility = serviceDao.getFacilityById(facilityId);
		return new FacilityRequest(facility);
	}

	public List<FacilityRequest> getAllFacilities() {
		List<Facility> facilityList = serviceDao.getAllFacilities();
		List<FacilityRequest> listOfFacilities = new ArrayList<FacilityRequest>();
		for (Facility facility : facilityList) {
			FacilityRequest facilityRequest = new FacilityRequest(facility);
			listOfFacilities.add(facilityRequest);
		}
		return listOfFacilities;
	}

	public void saveService(com.reedhousesystems.services.core.health.messenger.model.Service service) {
		serviceDao.saveService(service);
	}

	public ServiceRequest getServiceById(String serviceId) {
		com.reedhousesystems.services.core.health.messenger.model.Service service = serviceDao.getServiceById(serviceId);
		if (service == null) throw new ItemNotFoundException("Service Not Found", "Service requested cannot be found");
		return new ServiceRequest(service);
	}

	public List<ServiceRequest> getServicesByFacilityId(String facilityId) {
		List<com.reedhousesystems.services.core.health.messenger.model.Service> services = serviceDao.getServicesByFacilityId(facilityId);
		List<ServiceRequest> listOfServices = new ArrayList<ServiceRequest>();
		for (com.reedhousesystems.services.core.health.messenger.model.Service service : services) {
			ServiceRequest serviceRequest = new ServiceRequest(service);
			listOfServices.add(serviceRequest);
		}
		return listOfServices;
	}

	public ServiceRequest searchService(String name) {
		com.reedhousesystems.services.core.health.messenger.model.Service service = serviceDao.searchService(name);
		if (service == null)
			throw new ItemNotFoundException("Service Not Found", "Service requested not found");
		return new ServiceRequest(service);
	}
	
}