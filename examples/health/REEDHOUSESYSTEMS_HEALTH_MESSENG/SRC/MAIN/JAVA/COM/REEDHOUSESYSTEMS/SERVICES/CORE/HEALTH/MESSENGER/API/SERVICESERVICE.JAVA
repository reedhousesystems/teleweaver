package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.model.Facility;
import com.reedhousesystems.services.core.health.messenger.model.Service;
import com.reedhousesystems.services.core.health.messenger.request.FacilityRequest;
import com.reedhousesystems.services.core.health.messenger.request.ServiceRequest;

public interface ServiceService {
	
	public void saveFacility(Facility facility);
	
	public FacilityRequest getFacilityById(String facilityId);
	
	public List<FacilityRequest> getAllFacilities();
	
	public void saveService(Service service);
	
	public ServiceRequest getServiceById(String serviceId);

	public ServiceRequest searchService(String name);
	
	public List<ServiceRequest> getServicesByFacilityId(String facilityId);
	
}