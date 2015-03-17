package com.reedhousesystems.services.core.health.dashboard.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reedhousesystems.services.core.health.dashboard.api.ServiceDao;
import com.reedhousesystems.services.core.health.dashboard.api.ServiceService;
import com.reedhousesystems.services.core.health.dashboard.model.Address;
import com.reedhousesystems.services.core.health.dashboard.model.Facility;
import com.reedhousesystems.services.core.health.dashboard.model.ServiceType;
import com.reedhousesystems.services.core.health.dashboard.model.Services;
import com.reedhousesystems.services.core.health.dashboard.requests.FacilityRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.ServiceRequest;
import com.reedhousesystems.services.core.health.dashboard.requests.ServiceTypeRequest;

@Service("serviceService")
@Transactional
public class ServiceImpl implements ServiceService {
	
	@Resource
	private ServiceDao serviceDao;
	
	public void saveFacility(FacilityRequest request) throws Exception {
		try {
			Facility newFacility = createNewFacility(request);
			serviceDao.saveFacility(newFacility);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateFacility(String facilityId, FacilityRequest request) throws Exception {
		try {
			Facility facilityToUpdate = serviceDao.getFacilityById(facilityId);
			if (request.getName() != null) facilityToUpdate.setName(request.getName());
			if (request.getType() != null) facilityToUpdate.setType(request.getType());
			Address address = new Address(request.getAddress());
			facilityToUpdate.setAddress(address);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void deleteFacility(String facilityId) throws Exception {
		try {
			serviceDao.deleteFacility(facilityId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Facility getFacilityById(String facilityId) throws Exception {
		Facility facility = null;
		try {
			facility = serviceDao.getFacilityById(facilityId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return facility;
	}

	public List<Facility> getAllFacilities() throws Exception {
		List<Facility> facilityList = new ArrayList<Facility>();
		try {
			facilityList = serviceDao.getAllFacilities();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return facilityList;
	}
	
	public void saveService(ServiceRequest request) throws Exception {
		try {
			Services newService = createNewService(request);
			serviceDao.saveService(newService);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateService(String serviceId, ServiceRequest request) throws Exception {
		try {
			Services serviceToUpdate = serviceDao.getServiceById(serviceId);
			if (request.getName() != null) serviceToUpdate.setName(request.getName());
			if (request.getDescription() != null) serviceToUpdate.setDescription(request.getDescription());
			if (request.getType() != null) serviceToUpdate.setType(request.getType());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void deleteService(String serviceId) throws Exception {
		try {
			serviceDao.deleteService(serviceId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public ServiceRequest getServiceById(String serviceId) throws Exception {
		Services service = null;
		try {
			service = serviceDao.getServiceById(serviceId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ServiceRequest(service);
	}

	public List<ServiceRequest> getAllServices() throws Exception {
		List<Services> services = new ArrayList<Services>();
		List<ServiceRequest> serviceList = new ArrayList<ServiceRequest>();
		try {
			services = serviceDao.getAllServices();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (Services service : services) {
			ServiceRequest request = new ServiceRequest(service);
			serviceList.add(request);
		}
		return serviceList;
	}

	public List<Services> getServicesByFacilityId(String facilityId) throws Exception {
		List<Services> serviceList = new ArrayList<Services>();
		try {
			List<Services> servicesList = new ArrayList<Services>();
			try {
				Facility facility = serviceDao.getFacilityById(facilityId);
				servicesList = facility.getServices();
			} catch (HibernateException e) {	
				e.printStackTrace();
			}
			return servicesList;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return serviceList;
	}

	public List<Services> getServicesNotOfferedByAFacility(String facilityId) throws Exception {
		List<Services> serviceList = new ArrayList<Services>();
		try {
			serviceList = serviceDao.getServicesNotOfferedByAFacility(facilityId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return serviceList;
	}

	public void addServicesToAFacility(String serviceId, String facilityId) throws Exception {
		try {
			Services service = serviceDao.getServiceById(serviceId);
			Facility facility = getFacilityById(facilityId);
			List<Services> servicesList = facility.getServices();
			if (!servicesList.contains(service)) {
				servicesList.add(service);
				facility.setServices(servicesList);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void removeServicesFromAFacility(String serviceId, String facilityId) throws Exception {
		try {
			Services service = serviceDao.getServiceById(serviceId);
			Facility facility = getFacilityById(facilityId);
			List<Services> serviceList = facility.getServices();
			if (serviceList.contains(service))
				serviceList.remove(service);
			facility.setServices(serviceList);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void saveServiceType(ServiceTypeRequest request) throws Exception {
		try {
			ServiceType serviceTypeToSave = new ServiceType(request);
			serviceDao.saveServiceType(serviceTypeToSave);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateServiceType(String serviceTypeId, ServiceTypeRequest request) throws Exception {
		try {
			ServiceType serviceTypeToUpdate = serviceDao.getServiceTypeById(serviceTypeId);
			if (request.getName() != null) serviceTypeToUpdate.setName(request.getName());
			if (request.getDescription() != null) serviceTypeToUpdate.setDescription(request.getDescription());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void deleteServiceType(String serviceTypeId) throws Exception {
		try {
			serviceDao.deleteServiceType(serviceTypeId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public ServiceTypeRequest getServiceTypeById(String serviceTypeId) throws Exception {
		ServiceType serviceType = null;
		try {
			serviceType = serviceDao.getServiceTypeById(serviceTypeId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ServiceTypeRequest(serviceType);
	}

	public List<ServiceTypeRequest> getAllServiceTypes() throws Exception {
		List<ServiceType> serviceTypeList = new ArrayList<ServiceType>();
		List<ServiceTypeRequest> serviceTypes = new ArrayList<ServiceTypeRequest>();
		try {
			serviceTypeList = serviceDao.getAllServiceTypes();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (serviceTypes != null) {
			for (ServiceType serviceType : serviceTypeList) {
				serviceTypes.add(new ServiceTypeRequest(serviceType));
			}
		}
		return serviceTypes;
	}
	
	private Facility createNewFacility(FacilityRequest request) {
		Facility newFacility = new Facility(request);
		newFacility.setStatus("Unpublished");
		return newFacility;
	}
	
	private Services createNewService(ServiceRequest request) {
		Services newService = new Services(request);
		newService.setStatus("Unpublished");
		return newService;
	}
	
}