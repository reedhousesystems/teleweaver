package com.reedhousesystems.services.core.health.dashboard.api;

import java.util.List;

import com.reedhousesystems.services.core.health.dashboard.model.Facility;
import com.reedhousesystems.services.core.health.dashboard.model.Services;
import com.reedhousesystems.services.core.health.dashboard.requests.FacilityRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.ServiceRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.ServiceTypeRequest;

public interface ServiceService {
	
	public void saveFacility(FacilityRequest request) throws Exception;
	
	public void updateFacility(String facilityId, FacilityRequest request) throws Exception;
	
	public void deleteFacility(String facilityId) throws Exception;
	
	public Facility getFacilityById(String facilityId) throws Exception;
	
	public List<Facility> getAllFacilities() throws Exception;
	
	public void saveService(ServiceRequest request) throws Exception;
	
	public void updateService(String serviceId, ServiceRequest request) throws Exception;
	
	public void deleteService(String serviceId) throws Exception;
	
	public ServiceRequest getServiceById(String serviceId) throws Exception;
	
	public List<ServiceRequest> getAllServices() throws Exception;
	
	public void addServicesToAFacility(String serviceId, String facilityId) throws Exception;
	
	public void removeServicesFromAFacility(String serviceId, String facilityId) throws Exception;
	
	public List<Services> getServicesByFacilityId(String facilityId) throws Exception;
	
	public List<Services> getServicesNotOfferedByAFacility(String facilityId) throws Exception;
	
	public void saveServiceType(ServiceTypeRequest request) throws Exception;
	
	public void updateServiceType(String serviceTypeId, ServiceTypeRequest request) throws Exception;
	
	public void deleteServiceType(String serviceTypeId) throws Exception;
	
	public ServiceTypeRequest getServiceTypeById(String serviceTypeId) throws Exception;
	
	public List<ServiceTypeRequest> getAllServiceTypes() throws Exception;
	
}