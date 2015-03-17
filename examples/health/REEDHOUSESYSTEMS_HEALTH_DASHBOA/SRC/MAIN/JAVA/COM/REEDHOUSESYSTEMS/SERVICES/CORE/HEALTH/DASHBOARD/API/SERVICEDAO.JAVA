package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.model.Facility;
import com.reedhousesystems.services.core.health.dashboard.model.ServiceType;
import com.reedhousesystems.services.core.health.dashboard.model.Services;

public interface ServiceDao {
	
	public void saveFacility(Facility facility) throws Exception;
	
	public void deleteFacility(String facilityId) throws Exception;
	
	public Facility getFacilityById(String facilityId) throws Exception;
	
	public List<Facility> getAllFacilities() throws Exception;
	
	public void saveService(Services service) throws Exception;
	
	public void deleteService(String serviceId) throws Exception;
	
	public Services getServiceById(String serviceId) throws Exception;
	
	public List<Services> getAllServices() throws Exception;
	
	public List<Services> getServicesNotOfferedByAFacility(String facilityId) throws Exception;
	
	public void saveServiceType(ServiceType serviceType) throws Exception;
	
	public void deleteServiceType(String serviceTypeId) throws Exception;
	
	public ServiceType getServiceTypeById(String serviceTypeId) throws Exception;
	
	public List<ServiceType> getAllServiceTypes() throws Exception;
	
}