package com.reedhousesystems.services.core.health.messenger.api;

import java.util.List;

import com.reedhousesystems.services.core.health.messenger.model.Facility;
import com.reedhousesystems.services.core.health.messenger.model.Service;

public interface ServiceDao {
	
	public void saveFacility(Facility facility);
	
	public Facility getFacilityById(String facilityId);
	
	public List<Facility> getAllFacilities();
	
	public void saveService(Service service);
	
	public Service getServiceById(String serviceId);
	
	public Service searchService(String name);
	
	public List<Service> getServicesByFacilityId(String facilityId);
	
}